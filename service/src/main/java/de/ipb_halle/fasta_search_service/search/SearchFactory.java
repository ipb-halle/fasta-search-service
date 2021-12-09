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
package de.ipb_halle.fasta_search_service.search;

import java.io.File;
import java.io.IOException;

import de.ipb_halle.fasta_search_service.models.search.TranslationTable;
import de.ipb_halle.fasta_search_service.service.ProgramExecutionException;

/**
 * Prototype class responsible for a building parameter list for the fasta36
 * programs and executing sequence searches.
 * 
 * @author flange
 */
public abstract class SearchFactory {
	protected static final String PARAM_PROTEIN_QUERY = "-p";
	protected static final String PARAM_NUCLEOTIDE_QUERY = "-n";
	protected static final String PARAM_TRANSLATION_TABLE_FORMAT = "-t %d";
	private static final String PARAM_MAXRESULTS_FORMAT = "-d %d -b %d";

	private int maxResults = 50;

	/**
	 * @return name of the executable from the fasta36 program suite to be used for
	 *         search execution
	 */
	protected abstract String getProgramName();

	/**
	 * @return command parameters to be added when executing the search
	 */
	protected abstract String[] getParams();

	/**
	 * Changes the maximum number of search results.
	 * 
	 * @param maxResults
	 * @return this object
	 */
	public SearchFactory maxResults(int maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * Changes the translation table to be used in sequence searches.
	 * 
	 * @param table
	 * @return this object
	 */
	public SearchFactory translationTable(TranslationTable table) {
		return this;
	}

	/**
	 * Execute a sequence search.
	 * 
	 * @param queryFile
	 * @param libraryFile
	 * @param libraryFileFormat library format
	 * @param programExecutor 
	 * @return output of of the program execution
	 * @throws IOException
	 * @throws ProgramExecutionException in case the process execution took too long
	 */
	public ProgramOutput execSearch(File queryFile, File libraryFile, LibraryFileFormat libraryFileFormat,
			ProgramExecutor exec) throws IOException, ProgramExecutionException {
		String program = libraryFileFormat.getBinDirectory() + "/" + getProgramName();

		exec.addCommands(program, "-q", "-m", "10")
				.addCommands(getParams())
				.addCommands(String.format(PARAM_MAXRESULTS_FORMAT, maxResults, maxResults))
				.addCommands(queryFile.getAbsolutePath())
				.addCommands(libraryFile.getAbsolutePath() + " " + Integer.toString(libraryFileFormat.getNumber()));
		return exec.execute();
	}

	public interface Builder {
		public SearchFactory build();
	}
}