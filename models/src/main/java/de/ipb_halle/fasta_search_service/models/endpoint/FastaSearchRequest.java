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
package de.ipb_halle.fasta_search_service.models.endpoint;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO for search requests to the FastaSearchEndpoint endpoints.
 * 
 * @author flange
 */
@XmlRootElement
public class FastaSearchRequest {
	private String libraryFile;
	private FastaSearchQuery searchQuery;

	public String getLibraryFile() {
		return libraryFile;
	}

	public void setLibraryFile(String libraryFile) {
		this.libraryFile = libraryFile;
	}

	public FastaSearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(FastaSearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}

	@Override
	public String toString() {
		return "FastaSearchRequest ["
				+ "\n  libraryFile=" + libraryFile
				+ "\n, searchQuery=" + searchQuery
				+ "\n]";
	}
}