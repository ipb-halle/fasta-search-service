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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author flange
 */
public class ProgramExecutorTest {
	@Test
	public void test_addCommands() {
		ProgramExecutor executor = new ProgramExecutor();
		assertThat(executor.getCommandList(), hasSize(0));

		assertSame(executor, executor.addCommands(null, null));
		assertThat(executor.getCommandList(), hasSize(2)); // interesting, but ok ...

		assertSame(executor, executor.addCommands("abc"));
		assertThat(executor.getCommandList(), hasSize(3));
		assertEquals("abc", executor.getCommandList().get(2));
	}

	@Ignore
	@Test
	public void test_execute() {
	}
}