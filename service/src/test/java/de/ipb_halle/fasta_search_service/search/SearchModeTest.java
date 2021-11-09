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

import static de.ipb_halle.fasta_search_service.search.SearchMode.DNA_DNA;
import static de.ipb_halle.fasta_search_service.search.SearchMode.DNA_PROTEIN;
import static de.ipb_halle.fasta_search_service.search.SearchMode.PROTEIN_DNA;
import static de.ipb_halle.fasta_search_service.search.SearchMode.PROTEIN_PROTEIN;
import static de.ipb_halle.fasta_search_service.search.SequenceType.DNA;
import static de.ipb_halle.fasta_search_service.search.SequenceType.PROTEIN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.ipb_halle.fasta_search_service.search.factories.DNADNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.DNAProteinSearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinDNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinProteinSearchFactory;

/**
 * @author flange
 */
public class SearchModeTest {
	@Test
	public void test_numberOfItems() {
		assertThat("Please check the tests whenever you change the enum SearchMode!", SearchMode.values().length,
				is(4));
	}

	@Test
	public void test_getModeForSequenceTypes() {
		assertEquals(PROTEIN_PROTEIN, SearchMode.getSearchModeForSequenceTypes(PROTEIN, PROTEIN));
		assertEquals(DNA_DNA, SearchMode.getSearchModeForSequenceTypes(DNA, DNA));
		assertEquals(DNA_PROTEIN, SearchMode.getSearchModeForSequenceTypes(DNA, PROTEIN));
		assertEquals(PROTEIN_DNA, SearchMode.getSearchModeForSequenceTypes(PROTEIN, DNA));

		assertNull(SearchMode.getSearchModeForSequenceTypes(null, null));
		assertNull(SearchMode.getSearchModeForSequenceTypes(PROTEIN, null));
		assertNull(SearchMode.getSearchModeForSequenceTypes(null, PROTEIN));
	}

	@Test
	public void test_getNewSearchFactory() {
		assertTrue(PROTEIN_PROTEIN.getNewSearchFactory() instanceof ProteinProteinSearchFactory);
		assertTrue(DNA_DNA.getNewSearchFactory() instanceof DNADNASearchFactory);
		assertTrue(DNA_PROTEIN.getNewSearchFactory() instanceof DNAProteinSearchFactory);
		assertTrue(PROTEIN_DNA.getNewSearchFactory() instanceof ProteinDNASearchFactory);
	}
}