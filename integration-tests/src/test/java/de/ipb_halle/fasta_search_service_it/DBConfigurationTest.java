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
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.MountableFile;

import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service_it.util.TestUtils;

/**
 * @author flange
 */
public class DBConfigurationTest {
	private static final String postgresImageName = "postgres:13";
	private static final int dbPort = 5432;
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String table = "sequences";
	private static final String endpointName = "searchPostgres";

	private static String dbConnection2 = TestUtils.getDatabaseConnectionString("db2", dbPort, dbName, user, password);
	private static String proteinQueries = TestUtils.getDatabaseQueries(table, "PROTEIN");

	private static Logger logger = LoggerFactory.getLogger(ParameterTest.class);
	private static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

	private String uri;
	private Client client;

	private static Network network = Network.newNetwork();

	@ClassRule
	public static PostgreSQLContainer<?> postgres1 = new PostgreSQLContainer<>(postgresImageName)
			.withDatabaseName(dbName).withUsername(user).withPassword(password).withInitScript("init.sql")
			.withNetwork(network).withNetworkAliases("db1").withLogConsumer(logConsumer);

	@ClassRule
	public static PostgreSQLContainer<?> postgres2 = new PostgreSQLContainer<>(postgresImageName)
			.withDatabaseName(dbName).withUsername(user).withPassword(password).withInitScript("init_emptyTable.sql")
			.withNetwork(network).withNetworkAliases("db2").withLogConsumer(logConsumer);

	@ClassRule
	public static GenericContainer<?> fastaSearchContainer = new GenericContainer<>(TestUtils.getTestbuildImage())
			.withExposedPorts(8080).withNetwork(network).withLogConsumer(logConsumer)
			.withCopyFileToContainer(MountableFile.forClasspathResource("fasta-search-service.xml"),
					"/usr/local/tomee/conf/Catalina/localhost/");

	@Before
	public void init() {
		uri = "http://" + fastaSearchContainer.getHost() + ":" + fastaSearchContainer.getMappedPort(8080)
				+ "/fasta-search-service";
		client = ClientBuilder.newClient();
	}

	@Test
	public void test_withoutDatabaseConnectionString_useContainerConfiguration() {
		FastaSearchQuery query = new FastaSearchQuery();
		/*
		 * example from fasta-search-service/service/src/test/resources/de/ipb_halle/
		 * fasta_search_service/fastaresult/query7.fasta
		 */
		query.setQuerySequence("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setDatabaseQueries(proteinQueries);

		request.setDatabaseConnectionString(null);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertProteinSearchInProteinLibraryResult(searchResult);

		request.setDatabaseConnectionString("");
		response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		searchResult = response.readEntity(FastaSearchResult.class);
		FastaSearchResultAssertions.assertProteinSearchInProteinLibraryResult(searchResult);
	}

	@Test
	public void test_withDatabaseConnectionString() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setDatabaseQueries(proteinQueries);

		request.setDatabaseConnectionString(dbConnection2);
		Response response = client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getResults(), empty());
	}
}