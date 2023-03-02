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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.models.search.TranslationTable;
import de.ipb_halle.fasta_search_service_it.util.TestUtils;

/**
 * @author flange
 */
@Ignore("MariaDB searches do not work.")
public class MariaDBTest {
	private static final String mariaDBImageName = "mariadb:10.6";
	private static final int dbPort = 3306;
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String table = "sequences";
	private static final String initScript = "init.sql";
	private static final String endpointName = "searchMariaDB";

	private static String dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, dbName, user, password);
	private static String dnaQueries = TestUtils.getDatabaseQueries(table, "DNA");
	private static String proteinQueries = TestUtils.getDatabaseQueries(table, "PROTEIN");

	private static Logger logger = LoggerFactory.getLogger(MariaDBTest.class);
	private static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

	private String uri;
	private Client client;

	private static Network network = Network.newNetwork();

	@ClassRule
	public static MariaDBContainer<?> mariaDB = new MariaDBContainer<>(mariaDBImageName).withDatabaseName(dbName)
			.withUsername(user).withPassword(password).withInitScript(initScript).withNetwork(network)
			.withNetworkAliases("db").withLogConsumer(logConsumer);

	@ClassRule
	public static GenericContainer<?> fastaSearchContainer = new GenericContainer<>(TestUtils.getTestbuildImage())
			.withExposedPorts(8080).withNetwork(network).withLogConsumer(logConsumer);

	@Before
	public void init() {
		uri = "http://" + fastaSearchContainer.getHost() + ":" + fastaSearchContainer.getMappedPort(8080)
				+ "/fasta-search-service";
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(dnaQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_XML)
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(dnaQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_XML)
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		// maxResults = 0 does not override the default
		assertTrue(searchResult.getResults().size() > 1);

		query.setMaxResults(3);
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getProgramOutput(), not(emptyString()));
		String firstLine = searchResult.getProgramOutput().split("\n")[0];
		assertThat(firstLine, containsString("-b 3"));
		assertThat(firstLine, containsString("-d 3"));
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
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);

		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getProgramOutput(), not(emptyString()));
		assertThat(searchResult.getProgramOutput().split("\n")[0], containsString("\"-t 9\""));

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

		String dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, "wrongdatabase", user, password);
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertThat(errorMessage, containsString("could  not open database"));
		// Ohoh! The error message plots the library file including the DB credentials.
		assertThat(errorMessage, containsString("db:3306 wrongdatabase test testpassword"));
		assertThat(errorMessage, containsString("Access denied for user 'test'@'%' to database 'wrongdatabase'"));
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

		String dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, dbName, user, password);
		request.setDatabaseConnectionString(dbConnection);
		String queries = TestUtils.getDatabaseQueries("wrongtable",	"PROTEIN");
		request.setDatabaseQueries(queries);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertThat(errorMessage, containsString("Table 'integration-tests-db.wrongtable' doesn't exist"));
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
		request.setDatabaseQueries(proteinQueries);

		String dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, dbName, "wrongUser", password);
		request.setDatabaseConnectionString(dbConnection);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertThat(errorMessage, containsString("could  not open database"));
		// Ohoh! The error message plots the library file including the DB credentials.
		assertThat(errorMessage, containsString("db:3306 integration-tests-db wrongUser testpassword"));
		assertThat(errorMessage, containsString("Access denied for user 'wrongUser'@'"));
		assertThat(errorMessage, containsString("' (using password: YES)"));

		dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, dbName, user, "wrongPassword");
		request.setDatabaseConnectionString(dbConnection);
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		errorMessage = response.readEntity(String.class);
		assertThat(errorMessage, containsString("could  not open database"));
		assertThat(errorMessage, containsString("db:3306 integration-tests-db test wrongPassword"));
		assertThat(errorMessage, containsString("Access denied for user 'test'@'"));
		assertThat(errorMessage, containsString("' (using password: YES)"));
	}

	@Test
	public void test_noDatabaseConfiguration() {
		String expectedError = "Unable to find database connection information in the search request or in the service configuration.";
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"AAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setDatabaseQueries(proteinQueries);

		request.setDatabaseConnectionString(null);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedError, response.readEntity(String.class));

		request.setDatabaseConnectionString("");
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		assertEquals(expectedError, response.readEntity(String.class));
	}
}
