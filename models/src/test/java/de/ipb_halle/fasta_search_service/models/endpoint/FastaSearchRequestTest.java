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
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaSearchRequestTest {
	@Test
	public void test_defaultsAndGettersAndSetters() {
		FastaSearchRequest request = new FastaSearchRequest();

		assertNull(request.getDatabaseConnectionString());
		request.setDatabaseConnectionString("abc");
		assertEquals("abc", request.getDatabaseConnectionString());

		assertNull(request.getDatabaseQueries());
		request.setDatabaseQueries("def");;
		assertEquals("def", request.getDatabaseQueries());

		assertNull(request.getSearchQuery());
		FastaSearchQuery query = new FastaSearchQuery();
		request.setSearchQuery(query);
		assertSame(query, request.getSearchQuery());
	}
}