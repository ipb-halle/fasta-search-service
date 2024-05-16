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

import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * POJO for fasta sequence search queries.
 * 
 * @author flange
 */
@XmlRootElement
public class FastaSearchQuery {
	private String querySequence;
	private String querySequenceType;
	private String librarySequenceType;
	private int translationTable;
	private int maxResults;

	public String getQuerySequence() {
		return querySequence;
	}

	public void setQuerySequence(String querySequence) {
		this.querySequence = querySequence;
	}

	public String getQuerySequenceType() {
		return querySequenceType;
	}

	public void setQuerySequenceType(String querySequenceType) {
		this.querySequenceType = querySequenceType;
	}

	public String getLibrarySequenceType() {
		return librarySequenceType;
	}

	public void setLibrarySequenceType(String librarySequenceType) {
		this.librarySequenceType = librarySequenceType;
	}

	public int getTranslationTable() {
		return translationTable;
	}

	public void setTranslationTable(int translationTable) {
		this.translationTable = translationTable;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	@Override
	public String toString() {
		return "FastaSearchQuery ["
				+ "\n  querySequence=" + querySequence
				+ "\n, querySequenceType=" + querySequenceType
				+ "\n, librarySequenceType=" + librarySequenceType
				+ "\n, translationTable=" + translationTable
				+ "\n, maxResults=" + maxResults
				+ "\n]";
	}
}
