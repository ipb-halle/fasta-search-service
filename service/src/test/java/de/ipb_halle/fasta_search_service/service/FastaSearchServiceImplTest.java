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
package de.ipb_halle.fasta_search_service.service;

import static de.ipb_halle.fasta_search_service.search.LibraryFileFormat.MYSQL;
import static de.ipb_halle.fasta_search_service.search.LibraryFileFormat.POSTGRES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.ipb_halle.fasta_search_service.config.DatabaseConnectionConfigurationService;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.search.ProgramOutput;

/**
 * @author flange
 */
@RunWith(ApplicationComposer.class)
public class FastaSearchServiceImplTest {
	private FastaSearchQuery query;
	private FastaSearchRequest request;
	private FastaSearchResult result;

	@Inject
	private FastaSearchService service;

	@Inject
	private DatabaseConnectionConfigurationService dbConfigurationService;

	private ProgramExecutorMock programExecutorMock = ProgramExecutorFactoryMockProducer.getMockinstance();
	private FastaResultParserMock fastaResultParserMock = FastaResultParserMockProducer.getInstance();

	@Module
	@Classes(cdi = true, value = { FastaSearchServiceImpl.class, DatabaseConnectionConfigurationService.class,
			ProgramExecutorFactoryMockProducer.class, FastaResultParserMockProducer.class })
	public EjbJar app() {
		return new EjbJar();
	}

	@Before
	public void init() {
		query = new FastaSearchQuery();
		query.setQuerySequence("AGCTGA");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("PROTEIN");
		query.setTranslationTable(1);
		query.setMaxResults(10);
		request = new FastaSearchRequest();
		request.setDatabaseConnectionString("connect to my db!");
		request.setDatabaseQueries("SELECT * FROM sequences;");
		request.setSearchQuery(query);

		programExecutorMock.reset();
		fastaResultParserMock.setBehaviour(null);
	}

	@Test
	public void test_search_invalidRequest() {
		request = null;
		InvalidFastaSearchRequestException e = assertThrows(InvalidFastaSearchRequestException.class,
				() -> service.search(request, POSTGRES));
		assertEquals("Invalid request.", e.getMessage());

		request = new FastaSearchRequest();
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, MYSQL));
		assertEquals("Missing searchQuery.", e.getMessage());

		request.setSearchQuery(new FastaSearchQuery());
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, POSTGRES));
		assertEquals("Missing query sequence.", e.getMessage());

		request.getSearchQuery().setQuerySequence("");
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, MYSQL));
		assertEquals("Missing query sequence.", e.getMessage());

		request.getSearchQuery().setQuerySequence("abc");
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, POSTGRES));
		assertEquals("Missing database queries.", e.getMessage());

		request.setDatabaseQueries("");
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, MYSQL));
		assertEquals("Missing database queries.", e.getMessage());
	}

	@Test
	public void test_search_noDatabaseConnectionString() {
		String errorMsg = "Unable to find database connection information in the search request or in the service configuration.";
		assertFalse(dbConfigurationService.hasConfig());

		request.setDatabaseConnectionString(null);
		InvalidFastaSearchRequestException e = assertThrows(InvalidFastaSearchRequestException.class,
				() -> service.search(request, POSTGRES));
		assertEquals(errorMsg, e.getMessage());

		request.setDatabaseConnectionString("");
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, MYSQL));
		assertEquals(errorMsg, e.getMessage());
	}

	@Test
	public void test_search_invalidSearchMode() {
		String expectedMessage = "Could not determine the search mode from the given querySequenceType and librarySequenceType.";

		request.getSearchQuery().setQuerySequenceType("UNKNOWN");
		request.getSearchQuery().setLibrarySequenceType("DNA");
		InvalidFastaSearchRequestException e = assertThrows(InvalidFastaSearchRequestException.class,
				() -> service.search(request, POSTGRES));
		assertEquals(expectedMessage, e.getMessage());

		request.getSearchQuery().setQuerySequenceType("DNA");
		request.getSearchQuery().setLibrarySequenceType("UNKNOWN");
		e = assertThrows(InvalidFastaSearchRequestException.class, () -> service.search(request, POSTGRES));
		assertEquals(expectedMessage, e.getMessage());
	}

	@Test
	public void test_search_programExecutorThrowsIOException() {
		programExecutorMock.setBehaviour(() -> {
			throw new IOException("bad things happen");
		});
		IOException e = assertThrows(IOException.class, () -> service.search(request, POSTGRES));
		assertEquals("bad things happen", e.getMessage());
	}

	@Test
	public void test_search_programExecutorThrowsProgramExecutionException() {
		programExecutorMock.setBehaviour(() -> {
			throw new ProgramExecutionException("oops");
		});
		ProgramExecutionException e = assertThrows(ProgramExecutionException.class, () -> service.search(request, POSTGRES));
		assertEquals("oops", e.getMessage());
	}

	@Test
	public void test_search_programExecutorReturnsError() {
		programExecutorMock.setBehaviour(() -> new ProgramOutput(1, "stdout", "something's wrong"));
		ProgramExecutionException e = assertThrows(ProgramExecutionException.class,
				() -> service.search(request, POSTGRES));
		assertEquals("fasta program returned with exit status 1\nError log:\nsomething's wrong", e.getMessage());
	}

	@Test
	public void test_search_checkCommandlineGivenToProgramExecutor() throws FastaResultParserException,
			InvalidFastaSearchRequestException, IOException, ProgramExecutionException {
		fastaResultParserMock.setBehaviour(input -> new ArrayList<>());
		programExecutorMock
				.setBehaviour(() -> new ProgramOutput(0, String.join(" ", programExecutorMock.getCommandList()), ""));

		/*
		 * permute querySequenceType and librarySequenceType
		 */
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("DNA");
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fasta36 -q -m 10 -n -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		query.setQuerySequenceType("protein");
		query.setLibrarySequenceType("DNA");
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/tfastx36 -q -m 10 -p -t 1 -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		query.setQuerySequenceType("dna");
		query.setLibrarySequenceType("protein");
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 1 -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fasta36 -q -m 10 -p -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		/*
		 * change libraryFileFormat
		 */
		result = service.search(request, MYSQL);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_mariadb/bin/fasta36 -q -m 10 -p -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("16"));
		programExecutorMock.getCommandList().clear();

		/*
		 * change translationTable
		 */
		query.setTranslationTable(2);
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fasta36 -q -m 10 -p -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("PROTEIN");
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 2 -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		// This translation table does not exist - back to default.
		query.setTranslationTable(-1000);
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 1 -d 10 -b 10"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		/*
		 * change maxResults
		 */
		query.setMaxResults(100);
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 1 -d 100 -b 100"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();

		// Not valid - back to default.
		query.setMaxResults(0);
		result = service.search(request, POSTGRES);
		assertThat(result.getProgramOutput(),
				startsWith("/usr/local/fasta36_postgresql/bin/fastx36 -q -m 10 -n -t 1 -d 50 -b 50"));
		assertThat(result.getProgramOutput(), endsWith("17"));
		programExecutorMock.getCommandList().clear();
	}

	@Test
	public void test_search_checkQuerySequenceFile() throws FastaResultParserException,
			InvalidFastaSearchRequestException, IOException, ProgramExecutionException {
		fastaResultParserMock.setBehaviour(input -> new ArrayList<>());
		programExecutorMock.setBehaviour(() -> new ProgramOutput(0, "", ""));

		result = service.search(request, POSTGRES);
		assertEquals(programExecutorMock.getQuerySequenceFileContent(), ">query\nAGCTGA");
	}

	@Test
	public void test_search_checkLibraryFileWithDifferentConfigurations() throws FastaResultParserException,
			InvalidFastaSearchRequestException, IOException, ProgramExecutionException {
		fastaResultParserMock.setBehaviour(input -> new ArrayList<>());
		programExecutorMock.setBehaviour(() -> new ProgramOutput(0, "", ""));

		assertFalse(dbConfigurationService.hasConfig());
		result = service.search(request, POSTGRES);
		assertEquals(programExecutorMock.getLibraryFileContent(), "connect to my db!\nSELECT * FROM sequences;");

		programExecutorMock.reset();
		programExecutorMock.setBehaviour(() -> new ProgramOutput(0, "", ""));
		dbConfigurationService.setDbHostname("dbhost");
		dbConfigurationService.setDbPort(1234);
		dbConfigurationService.setDbName("dbname");
		dbConfigurationService.setDbUser("user");
		dbConfigurationService.setDbPassword("pw");
		dbConfigurationService.init();
		assertTrue(dbConfigurationService.hasConfig());
		result = service.search(request, POSTGRES);
		assertEquals(programExecutorMock.getLibraryFileContent(), "connect to my db!\nSELECT * FROM sequences;");

		programExecutorMock.reset();
		programExecutorMock.setBehaviour(() -> new ProgramOutput(0, "", ""));
		request.setDatabaseConnectionString(null);
		result = service.search(request, POSTGRES);
		assertEquals(programExecutorMock.getLibraryFileContent(),
				"dbhost:1234 dbname user pw\nSELECT * FROM sequences;");
	}

	@Test
	public void test_search_limitByMaxResults() throws FastaResultParserException, InvalidFastaSearchRequestException,
			IOException, ProgramExecutionException {
		programExecutorMock.setBehaviour(() -> new ProgramOutput(0, "", ""));
		fastaResultParserMock.setBehaviour(input -> {
			List<FastaResult> results = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				FastaResult r = new FastaResult();
				r.setExpectationValue(i / 100.0);
				results.add(r);
			}
			Collections.shuffle(results);
			return results;
		});

		query.setMaxResults(1000);
		result = service.search(request, POSTGRES);
		assertThat(result.getResults(), hasSize(100));

		query.setMaxResults(50);
		result = service.search(request, POSTGRES);
		assertThat(result.getResults(), hasSize(50));
		assertEquals(0.01d, result.getResults().get(1).getExpectationValue(), 0.001d);
		assertEquals(0.49d, result.getResults().get(49).getExpectationValue(), 0.001d);

		query.setMaxResults(0);
		result = service.search(request, POSTGRES);
		assertThat(result.getResults(), hasSize(100));
	}
}