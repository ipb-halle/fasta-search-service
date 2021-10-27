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

import static de.ipb_halle.fasta_search_service.search.SequenceType.DNA;
import static de.ipb_halle.fasta_search_service.search.SequenceType.PROTEIN;

import de.ipb_halle.fasta_search_service.search.factories.DNADNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.DNAProteinSearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinDNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinProteinSearchFactory;

/**
 * Search modes supported by the fasta36 program suite.
 * 
 * @author flange
 */
public enum SearchMode {
	PROTEIN_PROTEIN(PROTEIN, PROTEIN, new ProteinProteinSearchFactory.Builder()),
//	RNA_RNA(RNA, RNA, new RNARNASearchFactory.Builder()),
	DNA_DNA(DNA, DNA, new DNADNASearchFactory.Builder()),
	DNA_PROTEIN(DNA, PROTEIN, new DNAProteinSearchFactory.Builder()),
	PROTEIN_DNA(PROTEIN, DNA, new ProteinDNASearchFactory.Builder());

	private final SequenceType querySequenceType;
	private final SequenceType sequenceLibraryType;
	private final SearchFactory.Builder searchFactoryBuilder;

	private SearchMode(SequenceType querySequenceType, SequenceType sequenceLibraryType,
			SearchFactory.Builder searchFactoryBuilder) {
		this.querySequenceType = querySequenceType;
		this.sequenceLibraryType = sequenceLibraryType;
		this.searchFactoryBuilder = searchFactoryBuilder;
	}

	/**
	 * Tries to find a matching {@link SearchMode} item for the given sequence types
	 * of the query and the library.
	 * 
	 * @param querySequenceType
	 * @param sequenceLibraryType
	 * @return matching item or {@code null} in case no match was found
	 */
	public static SearchMode getModeForSequenceTypes(SequenceType querySequenceType, SequenceType sequenceLibraryType) {
		for (SearchMode mode : values()) {
			if (mode.querySequenceType.equals(querySequenceType)
					&& mode.sequenceLibraryType.equals(sequenceLibraryType)) {
				return mode;
			}
		}

		return null;
	}

	/**
	 * @return new {@link SearchFactory} object specific for this kind of sequence
	 *         search
	 */
	public SearchFactory getNewSearchFactory() {
		return searchFactoryBuilder.build();
	}
}