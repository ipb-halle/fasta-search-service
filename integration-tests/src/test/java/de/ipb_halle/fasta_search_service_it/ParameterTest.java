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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
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
public class ParameterTest {
	private static final String postgresImageName = "postgres:13";
	private static final int dbPort = 5432;
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String table = "sequences";
	private static final String initScript = "init.sql";
	private static final String endpointName = "searchPostgres";

	private static String dbConnection = TestUtils.getDatabaseConnectionString("db", dbPort, dbName, user, password);
	private static String proteinQueries = TestUtils.getDatabaseQueries(table, "PROTEIN");

	private static Logger logger = LoggerFactory.getLogger(ParameterTest.class);
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
				+ "/fasta-search-service";
		client = ClientBuilder.newClient();
	}

	@After
	public void tearDown() {
		client.close();
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
		// maxResults <= 0 does not override the default
		String firstLine = searchResult.getProgramOutput().split("\n")[0];
		assertThat(firstLine, containsString("-b 50"));
		assertThat(firstLine, containsString("-d 50"));
		assertTrue(searchResult.getResults().size() > 1);

		query.setMaxResults(3);
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getProgramOutput(), not(emptyString()));
		firstLine = searchResult.getProgramOutput().split("\n")[0];
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
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setDatabaseConnectionString(dbConnection);
		request.setDatabaseQueries(proteinQueries);

		// Default translation table is STANDARD.
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertStandardTranslationTable(response);

		// Try with a different translation table.
		query.setTranslationTable(TranslationTable.ECHINODERM_MITOCHONDRIAL_FLATWORM_MITOCHONDRIAL.getId());
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));

		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getProgramOutput(), not(emptyString()));
		assertThat(searchResult.getProgramOutput().split("\n")[0], containsString("\"-t 9\""));

		FastaResult firstResult = searchResult.getResults().get(0);
		assertEquals("SAVQQNLAALENSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSNVMAA", firstResult.getQueryAlignmentLine());

		// Unknown translation table id: uses STANDARD
		query.setTranslationTable(-100);
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertStandardTranslationTable(response);
	}

	private void assertStandardTranslationTable(Response response) {
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getProgramOutput(), not(emptyString()));
		assertThat(searchResult.getProgramOutput().split("\n")[0], containsString("\"-t 1\""));

		FastaResult firstResult = searchResult.getResults().get(0);
		assertEquals("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", firstResult.getQueryAlignmentLine());
	}
}
