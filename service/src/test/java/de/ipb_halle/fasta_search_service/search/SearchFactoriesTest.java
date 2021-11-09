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

import static de.ipb_halle.fasta_search_service.search.LibraryFileFormat.MYSQL;
import static de.ipb_halle.fasta_search_service.search.LibraryFileFormat.POSTGRES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ipb_halle.fasta_search_service.search.factories.DNADNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.DNAProteinSearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinDNASearchFactory;
import de.ipb_halle.fasta_search_service.search.factories.ProteinProteinSearchFactory;

/**
 * @author flange
 */
public class SearchFactoriesTest {
	private File libraryFile;
	private File queryFile;
	private ProgramExecutor mockProgramExecutor;

	@Before
	public void init() throws IOException {
		libraryFile = createTempFile();
		queryFile = createTempFile();
	}

	@After
	public void cleanUp() {
		deleteFileIfNotNull(queryFile);
		deleteFileIfNotNull(libraryFile);
	}

	@Test
	public void test_DNADNASearchFactory() {
		SearchFactory factory = new DNADNASearchFactory.Builder().build();

		assertTrue(factory instanceof DNADNASearchFactory);
		assertEquals("fasta36", factory.getProgramName());
		assertSame(factory, factory.translationTable(TranslationTable.ALTERNATIVE_YEAST_NUCLEAR));
		assertArrayEquals(new String[] { "-n" }, factory.getParams());
	}

	@Test
	public void test_ProteinProteinSearchFactory() {
		SearchFactory factory = new ProteinProteinSearchFactory.Builder().build();

		assertTrue(factory instanceof ProteinProteinSearchFactory);
		assertEquals("fasta36", factory.getProgramName());
		assertSame(factory, factory.translationTable(TranslationTable.BACTERIAL_AND_PLANT_PLASTID));
		assertArrayEquals(new String[] { "-p" }, factory.getParams());
	}

	@Test
	public void test_DNAProteinSearchFactory() {
		SearchFactory factory = new DNAProteinSearchFactory.Builder().build();

		assertTrue(factory instanceof DNAProteinSearchFactory);
		assertEquals("fastx36", factory.getProgramName());
		// expect standard translation table as default
		assertArrayEquals(new String[] { "-n", "-t 1" }, factory.getParams());

		assertSame(factory, factory.translationTable(TranslationTable.ASCIDIAN_MITOCHONDRIAL));
		assertArrayEquals(new String[] { "-n", "-t 13" }, factory.getParams());
	}

	@Test
	public void test_ProteinDNASearchFactory() {
		SearchFactory factory = new ProteinDNASearchFactory.Builder().build();

		assertTrue(factory instanceof ProteinDNASearchFactory);
		assertEquals("tfastx36", factory.getProgramName());
		// expect standard translation table as default
		assertArrayEquals(new String[] { "-p", "-t 1" }, factory.getParams());

		assertSame(factory, factory.translationTable(TranslationTable.SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL));
		assertArrayEquals(new String[] { "-p", "-t 22" }, factory.getParams());
	}

	@Test
	public void test_maxResults() throws IOException {
		SearchFactory factory = new DNADNASearchFactory.Builder().build();
		addMocksToFactory(factory);

		factory.execSearch(queryFile, libraryFile, MYSQL);
		assertThat(mockProgramExecutor.getCommandList(), hasItem("-d 50 -b 50"));

		addMocksToFactory(factory);
		assertSame(factory, factory.maxResults(1000));
		factory.execSearch(queryFile, libraryFile, POSTGRES);
		assertThat(mockProgramExecutor.getCommandList(), hasItem("-d 1000 -b 1000"));
	}

	@Test
	public void test_execSearch() throws IOException {
		SearchFactory factory;
		String expected;

		factory = new DNADNASearchFactory.Builder().build();
		addMocksToFactory(factory);
		expected = "/usr/local/fasta36_mariadb/bin/fasta36 -q -m 10 -n -d 50 -b 50 " + queryFile.getAbsolutePath() + " "
				+ libraryFile.getAbsolutePath() + " 16";
		assertEquals(expected, factory.execSearch(queryFile, libraryFile, MYSQL));

		factory = new DNAProteinSearchFactory.Builder().build();
		addMocksToFactory(factory);
		expected = "/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 1 -d 50 -b 50 " + queryFile.getAbsolutePath() + " "
				+ libraryFile.getAbsolutePath() + " 17";
		assertEquals(expected, factory.execSearch(queryFile, libraryFile, POSTGRES));

		factory = new ProteinDNASearchFactory.Builder().build();
		addMocksToFactory(factory);
		expected = "/usr/local/fasta36_mariadb/bin/tfastx36 -q -m 10 -p -t 1 -d 50 -b 50 " + queryFile.getAbsolutePath() + " "
				+ libraryFile.getAbsolutePath() + " 16";
		assertEquals(expected, factory.execSearch(queryFile, libraryFile, MYSQL));

		factory = new ProteinProteinSearchFactory.Builder().build();
		addMocksToFactory(factory);
		expected = "/usr/local/fasta36_postgresql/bin/fasta36 -q -m 10 -p -d 50 -b 50 " + queryFile.getAbsolutePath() + " "
				+ libraryFile.getAbsolutePath() + " 17";
		assertEquals(expected, factory.execSearch(queryFile, libraryFile, POSTGRES));
	}

	private void addMocksToFactory(SearchFactory factory) {
		mockProgramExecutor = new ProgramExecutor() {
			@Override
			public String execute() throws IOException {
				return String.join(" ", getCommandList());
			}
		};
		factory.setProgramExecutorFactory(new ProgramExecutorFactory() {
			@Override
			public ProgramExecutor createNewInstance() {
				return mockProgramExecutor;
			}
		});
	}

	private File createTempFile() throws IOException {
		return File.createTempFile("test", null);
	}

	private void deleteFileIfNotNull(File file) {
		if (file != null) {
			file.delete();
		}
	}
}