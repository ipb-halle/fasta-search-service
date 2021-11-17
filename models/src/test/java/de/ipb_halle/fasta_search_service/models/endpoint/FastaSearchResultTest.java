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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;

/**
 * @author flange
 */
public class FastaSearchResultTest {
	@Test
	public void test_defaultsAndGettersAndSetters() {
		FastaSearchResult result = new FastaSearchResult();

		assertNull(result.getResults());
		List<FastaResult> results = new ArrayList<>();
		result.setResults(results);
		assertSame(results, result.getResults());

		assertNull(result.getProgramOutput());
		result.setProgramOutput("abc");
		assertEquals("abc", result.getProgramOutput());
	}
}