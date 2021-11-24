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

import java.io.IOException;

import javax.ejb.Local;

import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;

/**
 * Perform sequence similarity searches using the fasta program suite.
 * 
 * @author flange
 */
@Local
public interface FastaSearchService {
	/**
	 * Execute a search.
	 * 
	 * @param request search request
	 * @param format  format of the library file, may not be {@code null}
	 * @return search result
	 * @throws FastaResultParserException         in case the result file from the
	 *                                            fasta program cannot be parsed
	 * @throws InvalidFastaSearchRequestException in case the {@code request} object
	 *                                            is invalid
	 * @throws IOException                        in case an I/O error occurred
	 * @throws ProgramExecutionException          in case the fasta program returns
	 *                                            a failure
	 */
	public FastaSearchResult search(FastaSearchRequest request, LibraryFileFormat format)
			throws FastaResultParserException, InvalidFastaSearchRequestException, IOException,
			ProgramExecutionException;
}