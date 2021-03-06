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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaSearchQueryTest {
	@Test
	public void test_defaultsAndGettersAndSetters() {
		FastaSearchQuery query = new FastaSearchQuery();

		assertNull(query.getQuerySequence());
		query.setQuerySequence("abc");
		assertEquals("abc", query.getQuerySequence());

		assertNull(query.getQuerySequenceType());
		query.setQuerySequenceType("def");
		assertEquals("def", query.getQuerySequenceType());

		assertNull(query.getLibrarySequenceType());
		query.setLibrarySequenceType("ghi");
		assertEquals("ghi", query.getLibrarySequenceType());

		assertEquals(0, query.getTranslationTable());
		query.setTranslationTable(42);
		assertEquals(42, query.getTranslationTable());

		assertEquals(0, query.getMaxResults());
		query.setMaxResults(10);
		assertEquals(10, query.getMaxResults());
	}
}