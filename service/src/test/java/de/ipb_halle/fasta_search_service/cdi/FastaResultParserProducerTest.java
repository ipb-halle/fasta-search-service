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
package de.ipb_halle.fasta_search_service.cdi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import jakarta.inject.Inject;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParser;

/**
 * @author flange
 */
@RunWith(ApplicationComposer.class)
public class FastaResultParserProducerTest {
	@Inject
	private FastaResultParser obj1;

	@Inject
	private FastaResultParser obj2;

	@Module
	@Classes(cdi = true, value = { FastaResultParserProducer.class })
	public EjbJar app() {
		return new EjbJar();
	}

	@Test
	public void test_produce() {
		assertNotNull(obj1);
		assertNotNull(obj2);
		assertNotSame(obj1, obj2);
	}
}
