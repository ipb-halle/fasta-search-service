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

import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchResult;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;

/**
 * 
 * 
 * @author flange
 */
public interface FastaSearchService {
	public FastaSearchResult search(FastaSearchRequest request, LibraryFileFormat format)
			throws InvalidFastaSearchRequestException, IOException, FastaResultParserException;
}