/*
 * fasta-search-service
 * Copyright 2021 Leibniz-Institut für Pflanzenbiochemie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package de.ipb_halle.fasta_search_service.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchResult;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParser;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;
import de.ipb_halle.fasta_search_service.search.ProgramOutput;
import de.ipb_halle.fasta_search_service.search.SearchFactory;
import de.ipb_halle.fasta_search_service.search.SearchMode;
import de.ipb_halle.fasta_search_service.search.SequenceType;
import de.ipb_halle.fasta_search_service.search.TranslationTable;
import de.ipb_halle.fasta_search_service.util.FastaFileFormatUtils;

/**
 * 
 * @author flange
 */
@Stateless
public class FastaSearchServiceImpl implements FastaSearchService {
	@Override
	public FastaSearchResult search(FastaSearchRequest request, LibraryFileFormat format)
			throws InvalidFastaSearchRequestException, IOException, FastaResultParserException, ProgramExecutionException {
		FastaSearchQuery searchQuery = request.getSearchQuery();
		String querySequence = FastaFileFormatUtils.toFastaFileFormat(searchQuery.getQuerySequence());

		if (querySequence == null) {
			return emptyResult();
		}

		FastaSearchResult result = null;
		File querySequenceFile = null;
		File libraryFile = null;

		try {
			querySequenceFile = createQuerySequenceFile(querySequence);
			libraryFile = createLibraryFile(request.getLibraryFile());

			SearchMode mode = determineSearchMode(searchQuery.getQuerySequenceType(),
					searchQuery.getLibrarySequenceType());
			if (mode == null) {
				throw new InvalidFastaSearchRequestException(
						"Could not determine the search mode from the given querySequenceType and librarySequenceType.");
			}

			result = execSearchAndParseResults(libraryFile, format, querySequenceFile, mode, searchQuery);
		} finally {
			deleteFileIfNotNull(querySequenceFile);
			deleteFileIfNotNull(libraryFile);
		}

		return result;
	}

	private FastaSearchResult emptyResult() {
		return new FastaSearchResult(new ArrayList<>(), "");
	}

	private File createQuerySequenceFile(String querySequence) throws IOException {
		return writeToTempFile("FastaQuery", ".fasta", querySequence);
	}

	private File createLibraryFile(String libraryFile) throws IOException {
		return writeToTempFile("FastaLibrary", ".tmp", libraryFile);
	}

	private File writeToTempFile(String prefix, String suffix, String content) throws IOException {
		// create a file in /tmp
		File file = File.createTempFile(prefix, suffix);

		// just in case we forget to delete it
		file.deleteOnExit();

		try (FileWriter writer = new FileWriter(file)) {
			writer.write(content);
		}

		return file;
	}

	private void deleteFileIfNotNull(File file) {
		if (file != null) {
			file.delete();
		}
	}

	private SearchMode determineSearchMode(String querySequenceTypeAsString, String librarySequenceTypeAsString) {
		SequenceType querySequenceType = SequenceType.valueOf(querySequenceTypeAsString.toUpperCase());
		SequenceType librarySequenceType = SequenceType.valueOf(librarySequenceTypeAsString.toUpperCase());

		return SearchMode.getSearchModeForSequenceTypes(querySequenceType, librarySequenceType);
	}

	private FastaSearchResult execSearchAndParseResults(File libraryFile, LibraryFileFormat format,
			File querySequenceFile, SearchMode searchMode, FastaSearchQuery searchQuery)
			throws IOException, FastaResultParserException, ProgramExecutionException {
		ProgramOutput fastaProgramOutput = execFastaProgram(libraryFile, format, querySequenceFile, searchMode,
				searchQuery);

		checkForErrors(fastaProgramOutput);

		String output = fastaProgramOutput.getStdout();
		List<FastaResult> parsedResults = new FastaResultParser(new StringReader(output)).parse();
		return new FastaSearchResult(parsedResults, output);
	}

	private ProgramOutput execFastaProgram(File libraryFile, LibraryFileFormat format, File querySequenceFile,
			SearchMode searchMode, FastaSearchQuery searchQuery) throws IOException {
		SearchFactory factory = searchMode.getNewSearchFactory();

		int maxResults = searchQuery.getMaxResults();
		if (maxResults > 0) {
			factory.maxResults(maxResults);
		}

		TranslationTable translationTable = TranslationTable.fromId(searchQuery.getTranslationTable());
		if (translationTable != null) {
			factory.translationTable(translationTable);
		}

		return factory.execSearch(querySequenceFile, libraryFile, format);
	}

	private void checkForErrors(ProgramOutput fastaProgramOutput) throws ProgramExecutionException {
		int exitValue = fastaProgramOutput.getExitValue();
		if (exitValue != 0) {
			throw new ProgramExecutionException("fasta program returned with exit status " + exitValue
					+ "\nError log:\n" + fastaProgramOutput.getStderr());
		}
	}
}