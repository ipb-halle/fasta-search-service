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
package de.ipb_halle.fasta_search_service.search.factories;

import de.ipb_halle.fasta_search_service.search.SearchFactory;

/**
 * {@link SearchFactory} implementation for searches with protein query
 * sequences in protein libraries.
 * 
 * @author flange
 */
public class ProteinProteinSearchFactory extends SearchFactory {
	private static final String PROGRAM_NAME = "fasta36";

	@Override
	public String getProgramName() {
		return PROGRAM_NAME;
	}

	@Override
	public String[] getParams() {
		return new String[] { PARAM_PROTEIN_QUERY };
	}

	public static class Builder implements SearchFactory.Builder {
		@Override
		public SearchFactory build() {
			return new ProteinProteinSearchFactory();
		}
	}
}