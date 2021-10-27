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

import java.util.ArrayList;
import java.util.List;

import de.ipb_halle.fasta_search_service.search.SearchFactory;
import de.ipb_halle.fasta_search_service.search.TranslationTable;

/**
 * {@link SearchFactory} implementation for searches with protein query
 * sequences in DNA libraries.
 * 
 * @author flange
 */
public class ProteinDNASearchFactory extends SearchFactory {
	private static final String PROGRAM_NAME = "tfastx36";
	private TranslationTable translationTable = TranslationTable.STANDARD;

	@Override
	public String getProgramName() {
		return PROGRAM_NAME;
	}

	@Override
	public String[] getParams() {
		List<String> params = new ArrayList<>();
		params.add(PARAM_PROTEIN_QUERY);
		params.add(String.format(PARAM_TRANSLATION_TABLE_FORMAT, translationTable.getId()));
		return (String[]) params.toArray(new String[0]);
	}

	@Override
	public SearchFactory translationTable(TranslationTable table) {
		this.translationTable = table;
		return this;
	}

	public static class Builder implements SearchFactory.Builder {
		@Override
		public SearchFactory build() {
			return new ProteinDNASearchFactory();
		}
	}
}