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
package de.ipb_halle.fasta_search_service_it;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchResult;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.search.TranslationTable;
import de.ipb_halle.fasta_search_service_it.util.TestUtils;

/**
 * @author flange
 */
public class PostgresTest {
	private static final String postgresImageName = "postgres:13";
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String table = "sequences";
	private static final String initScript = "init.sql";

	private static String dnaLibraryFile = TestUtils.getLibraryFile("db", 5432, dbName, user, password, table, "DNA");
	private static String proteinLibraryFile = TestUtils.getLibraryFile("db", 5432, dbName, user, password, table,
			"PROTEIN");

	private static Logger logger = LoggerFactory.getLogger(PostgresTest.class);
	private static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

	private String uri;
	private Client client;

	private static Network network = Network.newNetwork();

	@ClassRule
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(postgresImageName).withDatabaseName(dbName)
			.withUsername(user).withPassword(password).withInitScript(initScript).withNetwork(network)
			.withNetworkAliases("db").withLogConsumer(logConsumer);

	@ClassRule
	public static GenericContainer<?> fastaSearchContainer = new GenericContainer<>(TestUtils.getTestbuildImage())
			.withExposedPorts(8080).withNetwork(network).withLogConsumer(logConsumer);

	@Before
	public void init() {
		uri = "http://" + fastaSearchContainer.getHost() + ":" + fastaSearchContainer.getMappedPort(8080)
				+ "/Fasta-Search-Service";
		client = ClientBuilder.newClient();
	}

	@After
	public void tearDown() {
		client.close();
	}

	@Test
	public void test_DNASearchInDNALibrary() {
		FastaSearchQuery query = new FastaSearchQuery();
		/*
		 * example from fasta-search-service/service/src/test/resources/de/ipb_halle/
		 * fasta_search_service/fastaresult/query8.fasta
		 */
		query.setQuerySequence(
				"CCTGCCGATCTGGTTAACTACAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAACTGAGCGCGGCCGCTTTGCAGTACAGCGAC");
		query.setQuerySequenceType("dna");
		query.setLibrarySequenceType("DNA");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(dnaLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertDNASearchInDNALibraryResult(searchResult);
	}

	@Test
	public void test_DNASearchInProteinLibrary() {
		FastaSearchQuery query = new FastaSearchQuery();
		/*
		 * example from fasta-search-service/service/src/test/resources/de/ipb_halle/
		 * fasta_search_service/fastaresult/query9.fasta
		 */
		query.setQuerySequence(
				"CCTGCCGATCTGGTTAACTACAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAACTGAGCGCGGCCGCTTTGCAGTACAGCGAC");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("protein");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(proteinLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertDNASearchInProteinLibraryResult(searchResult);
	}

	@Test
	public void test_proteinSearchInProteinLibrary() {
		FastaSearchQuery query = new FastaSearchQuery();
		/*
		 * example from fasta-search-service/service/src/test/resources/de/ipb_halle/
		 * fasta_search_service/fastaresult/query7.fasta
		 */
		query.setQuerySequence(
				"SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("protein");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(proteinLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_XML)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertProteinSearchInProteinLibraryResult(searchResult);
	}

	@Test
	public void test_proteinSearchInDNALibrary() {
		FastaSearchQuery query = new FastaSearchQuery();
		/*
		 * example from fasta-search-service/service/src/test/resources/de/ipb_halle/
		 * fasta_search_service/fastaresult/query11.fasta
		 */
		query.setQuerySequence(
				"SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("dna");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(dnaLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertProteinSearchInDNALibraryResult(searchResult);
	}

	@Test
	public void test_maxResults() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		query.setMaxResults(0);
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(proteinLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		// maxResults = 0 does not override the default
		assertTrue(searchResult.getResults().size() > 1);

		query.setMaxResults(3);
		response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		searchResult = response.readEntity(FastaSearchResult.class);
		assertFalse(searchResult.getProgramOutput().isEmpty());
		String firstLine = searchResult.getProgramOutput().split("\n")[0];
		assertTrue(firstLine.contains("-b 3"));
		assertTrue(firstLine.contains("-d 3"));

		System.out.println(searchResult.getProgramOutput());
		assertThat(searchResult.getResults(), hasSize(3));
	}

	@Test
	public void test_translationTable() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"agcgcggtgcagcagaaactggcggcgctggaaaaaagcagcggcggccgcctgggcgtg"
				+ "gcgctgattgataccgcggataacacccaggtgctgtatcgcggcgatgaacgctttccg"
				+ "atgtgcagcaccagcaaagtgatggcggcg");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("PROTEIN");
		query.setTranslationTable(TranslationTable.ECHINODERM_MITOCHONDRIAL_FLATWORM_MITOCHONDRIAL.getId());
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(proteinLibraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertFalse(searchResult.getProgramOutput().isEmpty());
		assertTrue(searchResult.getProgramOutput().split("\n")[0].contains("\"-t 9\""));

		FastaResult firstResult = searchResult.getResults().get(0);
		// With the default translation table we would get "SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA".
		assertEquals("SAVQQNLAALENSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSNVMAA", firstResult.getQueryAlignmentLine());
	}

	@Test
	public void test_wrongDatabase() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"AAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);

		String libraryFile = TestUtils.getLibraryFile("db", 5432, "wrongdatabase", user, password, table,
				"PROTEIN");
		request.setLibraryFile(libraryFile);
		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertTrue(errorMessage.contains("Connection to database 'wrongdatabase' failed"));
		assertTrue(errorMessage.contains("database \"wrongdatabase\" does not exist"));
	}

	@Test
	public void test_wrongTable() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"AAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);

		String libraryFile = TestUtils.getLibraryFile("db", 5432, dbName, user, password, "wrongtable",
				"PROTEIN");
		request.setLibraryFile(libraryFile);
		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertTrue(errorMessage.contains("relation \"wrongtable\" does not exist"));
	}

	@Test
	public void test_wrongDBCredentials() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"AAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);

		String libraryFile = TestUtils.getLibraryFile("db", 5432, dbName, "wrongUser", password, table,
				"PROTEIN");
		request.setLibraryFile(libraryFile);
		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertTrue(errorMessage.contains("Connection to database 'integration-tests-db' failed"));
		assertTrue(errorMessage.contains("password authentication failed for user \"wrongUser\""));

		libraryFile = TestUtils.getLibraryFile("db", 5432, dbName, user, "wrongPassword", table,
				"PROTEIN");
		request.setLibraryFile(libraryFile);
		response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		errorMessage = response.readEntity(String.class);
		assertTrue(errorMessage.contains("Connection to database 'integration-tests-db' failed"));
		assertTrue(errorMessage.contains("password authentication failed for user \"test\""));
	}
}