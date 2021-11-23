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

import static org.junit.Assert.assertEquals;

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

import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service_it.util.TestUtils;

/**
 * @author flange
 */
public class ErrorTest {
	private static final String postgresImageName = "postgres:13";
	private static final int dbPort = 5432;
	private static final String dbName = "integration-tests-db";
	private static final String user = "test";
	private static final String password = "testpassword";
	private static final String table = "sequences";
	private static final String initScript = "init.sql";
	private static final String endpointName = "searchPostgres";

	private static String proteinLibraryFile = TestUtils.getLibraryFile("db", dbPort, dbName, user, password, table,
			"PROTEIN");

	private static Logger logger = LoggerFactory.getLogger(ParameterTest.class);
	private static Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(logger).withSeparateOutputStreams();

	private String uri;
	private Client client;

	private static Network network = Network.newNetwork();

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
	public void test_invalidQuerySequenceType() {
		FastaSearchRequest request = prepareRequest();
		request.getSearchQuery().setQuerySequenceType("UNKNOWN");
		Response response = execRequest(request);
		assertBadSearchMode(response);

		request.getSearchQuery().setQuerySequenceType("");
		response = execRequest(request);
		assertBadSearchMode(response);

		request.getSearchQuery().setQuerySequenceType(null);
		response = execRequest(request);
		assertBadSearchMode(response);
	}

	@Test
	public void test_invalidLibrarySequenceType() {
		FastaSearchRequest request = prepareRequest();
		request.getSearchQuery().setLibrarySequenceType("UNKNOWN");
		Response response = execRequest(request);
		assertBadSearchMode(response);

		request.getSearchQuery().setLibrarySequenceType("");
		response = execRequest(request);
		assertBadSearchMode(response);

		request.getSearchQuery().setLibrarySequenceType(null);
		response = execRequest(request);
		assertBadSearchMode(response);
	}

	private void assertBadSearchMode(Response response) {
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("Could not determine the search mode from the given querySequenceType and librarySequenceType.",
				errorMessage);
	}

	@Test
	public void test_invalidQuerySequence() {
		FastaSearchRequest request = prepareRequest();
		request.getSearchQuery().setQuerySequence(null);
		Response response = execRequest(request);
		assertInvalidQuerySequence(response);

		request.getSearchQuery().setQuerySequence("");
		response = execRequest(request);
		assertInvalidQuerySequence(response);
	}

	private void assertInvalidQuerySequence(Response response) {
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("Invalid query sequence.", errorMessage);
	}

	@Test
	public void test_invalidLibraryFile() {
		FastaSearchRequest request = prepareRequest();
		request.setLibraryFile(null);
		Response response = execRequest(request);
		assertInvalidLibraryFile(response);
		
		request.setLibraryFile("");
		response = execRequest(request);
		assertInvalidLibraryFile(response);
	}
	
	private void assertInvalidLibraryFile(Response response) {
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("Invalid library file.", errorMessage);
	}
	
	@Test
	public void test_missingFastaSearchQuery() {
		FastaSearchRequest request = prepareRequest();
		request.setSearchQuery(null);
		Response response = execRequest(request);
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("Missing searchQuery.", errorMessage);
	}

	private FastaSearchRequest prepareRequest() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA");
		query.setQuerySequenceType("PROTEIN");
		query.setLibrarySequenceType("PROTEIN");
		FastaSearchRequest request = new FastaSearchRequest();
		request.setSearchQuery(query);
		request.setLibraryFile(proteinLibraryFile);

		return request;
	}

	private Response execRequest(FastaSearchRequest request) {
		return client.target(uri).path(endpointName).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
	}
}