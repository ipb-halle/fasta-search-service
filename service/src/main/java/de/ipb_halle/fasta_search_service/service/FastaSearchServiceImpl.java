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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.EnumUtils;

import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParser;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.models.search.TranslationTable;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;
import de.ipb_halle.fasta_search_service.search.ProgramOutput;
import de.ipb_halle.fasta_search_service.search.SearchFactory;
import de.ipb_halle.fasta_search_service.search.SearchMode;
import de.ipb_halle.fasta_search_service.search.SequenceType;
import de.ipb_halle.fasta_search_service.util.FastaFileFormatUtils;

/**
 * 
 * @author flange
 */
@Stateless
public class FastaSearchServiceImpl implements FastaSearchService {
	@Override
	public FastaSearchResult search(FastaSearchRequest request, LibraryFileFormat format)
			throws FastaResultParserException, InvalidFastaSearchRequestException, IOException,
			ProgramExecutionException {
		checkFastaSearchRequest(request);

		FastaSearchQuery searchQuery = request.getSearchQuery();
		String querySequence = FastaFileFormatUtils.toFastaFileFormat(searchQuery.getQuerySequence());

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

	private void checkFastaSearchRequest(FastaSearchRequest request) throws InvalidFastaSearchRequestException {
		FastaSearchQuery searchQuery = request.getSearchQuery();
		if (searchQuery == null) {
			throw new InvalidFastaSearchRequestException("Missing searchQuery.");
		}
		checkQuerySequence(searchQuery.getQuerySequence());
		checkLibraryFile(request.getLibraryFile());
	}

	private void checkQuerySequence(String querySequence) throws InvalidFastaSearchRequestException {
		if ((querySequence == null) || (querySequence.isEmpty())) {
			throw new InvalidFastaSearchRequestException("Invalid query sequence.");
		}
	}

	private void checkLibraryFile(String libraryFile) throws InvalidFastaSearchRequestException {
		if ((libraryFile == null) || (libraryFile.isEmpty())) {
			throw new InvalidFastaSearchRequestException("Invalid library file.");
		}
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

	private SearchMode determineSearchMode(String query, String library) {
		SequenceType querySequenceType = EnumUtils.getEnumIgnoreCase(SequenceType.class, query, null);
		SequenceType librarySequenceType = EnumUtils.getEnumIgnoreCase(SequenceType.class, library, null);

		return SearchMode.getSearchModeForSequenceTypes(querySequenceType, librarySequenceType);
	}

	private FastaSearchResult execSearchAndParseResults(File libraryFile, LibraryFileFormat format,
			File querySequenceFile, SearchMode searchMode, FastaSearchQuery searchQuery)
			throws FastaResultParserException, ProgramExecutionException, IOException {
		ProgramOutput fastaProgramOutput = execFastaProgram(libraryFile, format, querySequenceFile, searchMode,
				searchQuery);

		checkForErrors(fastaProgramOutput);

		String output = fastaProgramOutput.getStdout();
		List<FastaResult> parsedResults = new FastaResultParser(new StringReader(output)).parse();

		parsedResults = sortResultsListAndApplyMaxResults(parsedResults, searchQuery.getMaxResults());

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

	/**
	 * Sort by E()-value, then limit the number of results to maxResults.
	 * 
	 * Why is this needed even though we limit the results via parameters of the
	 * fasta programs? In some cases, fasta supplies more alignment results than it
	 * is told to - might be a bug, or a feature?
	 */
	private List<FastaResult> sortResultsListAndApplyMaxResults(List<FastaResult> parsedResults, int maxResults) {
		if (maxResults < 1) {
			return parsedResults;
		}
		return parsedResults.stream().sorted(Comparator.comparing(result -> result.getExpectationValue()))
				.limit(maxResults).collect(Collectors.toList());
	}
}