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

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

/**
 * @author flange
 */
public class ProgramExecutorFactoryTest {
	@Test
	public void test_createNewInstance() {
		ProgramExecutorFactory factory = new ProgramExecutorFactory();
		ProgramExecutor obj1 = factory.createNewInstance();
		ProgramExecutor obj2 = factory.createNewInstance();
		assertNotSame(obj1, obj2);
	}
}