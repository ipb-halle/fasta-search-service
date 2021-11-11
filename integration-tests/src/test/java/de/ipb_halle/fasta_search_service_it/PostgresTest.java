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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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
import de.ipb_halle.fasta_search_service_it.util.TestUtils;

/**
 * @author flange
 */
public class PostgresTest {
	private static final String postgresImageName = "postgres:13";
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String initScript = "init.sql";

	private Logger logger = LoggerFactory.getLogger(PostgresTest.class);
	private Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

	private String uri;
	private FastaSearchRequest request;
	private Client client;

	private Network network = Network.newNetwork();

	@Rule
	public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(postgresImageName).withDatabaseName(dbName)
			.withUsername(user).withPassword(password).withInitScript(initScript).withNetwork(network)
			.withNetworkAliases("db").withLogConsumer(logConsumer);

	@Rule
	public GenericContainer<?> fastaSearchContainer = new GenericContainer<>(TestUtils.getTestbuildImage())
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
	public void test_DNA() {
		String libraryFile = TestUtils.getLibraryFile("db", 5432, dbName, user, password, "DNA");

		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence(
				"CCTGCCGATCTGGTTAACTACAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAACTGAGCGCGGCCGCTTTGCAGTACAGCGAC");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("DNA");
		request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(libraryFile);

		Response response = client.target(uri).path("searchPostgres").request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));

		System.out.println(response.getStatus());
		System.out.println(response.readEntity(FastaSearchResult.class).toString());
	}
}