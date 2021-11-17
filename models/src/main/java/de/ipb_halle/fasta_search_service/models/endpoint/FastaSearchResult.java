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

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;

/**
 * POJO for search results, which the FastaSearchEndpoint endpoints return.
 * 
 * @author flange
 */
@XmlRootElement
public class FastaSearchResult {
	private List<FastaResult> results;
	private String programOutput;

	public FastaSearchResult() {
	}

	public FastaSearchResult(List<FastaResult> results, String output) {
		this.results = results;
		this.programOutput = output;
	}

	public List<FastaResult> getResults() {
		return results;
	}

	public void setResults(List<FastaResult> results) {
		this.results = results;
	}

	public String getProgramOutput() {
		return programOutput;
	}

	public void setProgramOutput(String programOutput) {
		this.programOutput = programOutput;
	}

	@Override
	public String toString() {
		return "FastaSearchResult ["
				+ "\n  results=" + results
				+ "\n, programOutput=" + programOutput
				+ "\n]";
	}
}