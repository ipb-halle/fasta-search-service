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
package de.ipb_halle.fasta_search_service_it.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author flange
 */
public class TestUtilsTest {
	@Test
	public void test_getDatabaseConnectionString() {
		String dbConnection = TestUtils.getDatabaseConnectionString("dbHostname", 5432, "dbName", "dbUser", "dbPassword");
		assertEquals("dbHostname:5432 dbName dbUser dbPassword", dbConnection);
	}

	@Test
	public void test_getDatabaseQueries() {
		String libraryFile = TestUtils.getDatabaseQueries("sequences", "XYZ");
		String expected = ""
				+ "DO SELECT 1;\n"
				+ "SELECT id,sequence FROM sequences WHERE sequencetype = 'XYZ';\n"
				+ "SELECT description FROM sequences WHERE id=#;\n"
				+ "SELECT sequence FROM sequences WHERE id=#;";
		assertEquals(expected, libraryFile);
	}
}