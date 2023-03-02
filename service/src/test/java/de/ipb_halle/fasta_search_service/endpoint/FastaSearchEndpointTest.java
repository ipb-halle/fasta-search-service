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
package de.ipb_halle.fasta_search_service.endpoint;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.apache.openejb.testing.RandomPort;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.ipb_halle.fasta_search_service.cdi.LoggerProducer;
import de.ipb_halle.fasta_search_service.config.DatabaseConnectionConfigurationService;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.service.InvalidFastaSearchRequestException;
import de.ipb_halle.fasta_search_service.service.ProgramExecutionException;

/**
 * @author flange
 */
@EnableServices(value = "jaxrs", httpDebug = true)
@RunWith(ApplicationComposer.class)
public class FastaSearchEndpointTest {
	private static final String searchPostgres = "searchPostgres";
	private static final String searchMariaDB = "searchMariaDB";
	private static final String searchMySQL = "searchMySQL";

	@RandomPort("http")
	private int port;

	private String uri;
	private FastaSearchRequest request;
	private FastaResult result1, result2;
	private Client client;

	/*
	 * Note the @Local and @LocalBean annotations in FastaSearchService and
	 * FastaSearchServiceMockImpl, which allow to inject the class instead of the
	 * interface.
	 */
	@Inject
	private FastaSearchServiceMockImpl service;

	@Module
	@Classes(cdi = true, value = { FastaSearchEndpoint.class, FastaSearchServiceMockImpl.class,
			DatabaseConnectionConfigurationService.class, LoggerProducer.class })
	public WebApp app() {
		return new WebApp().contextRoot("test");
	}

	@Before
	public void init() {
		uri = "http://localhost:" + port + "/test";

		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence("AGCTGA");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("PROTEIN");
		query.setTranslationTable(1);
		query.setMaxResults(10);
		request = new FastaSearchRequest();
		request.setDatabaseConnectionString("connect to my db!");
		request.setSearchQuery(query);

		result1 = new FastaResult();
		result1.setOverlap(42);
		result2 = new FastaResult();
		result2.setConsensusLine(":.:");

		service.setBehaviour(
				request -> new FastaSearchResult(Arrays.asList(result1, result2), request.getDatabaseConnectionString()));

		client = ClientBuilder.newClient();
	}

	@After
	public void cleanup() {
		client.close();
	}

	private void assertResponseWithDefaultBehaviour(Response response) {
		assertEquals(Status.OK, Status.fromStatusCode(response.getStatus()));
		FastaSearchResult searchResult = response.readEntity(FastaSearchResult.class);
		assertThat(searchResult.getResults(), hasSize(2));
		assertEquals(result1, searchResult.getResults().get(0));
		assertEquals(result2, searchResult.getResults().get(1));
		assertEquals("connect to my db!", searchResult.getProgramOutput());
	}

	/*
	 * Tests for searchPostgres endpoint
	 */
	@Test
	public void test_searchPostgres_postJsonGetJson() {
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Test
	public void test_searchPostgres_postJsonGetXml() {
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Test
	public void test_searchPostgres_postXmlGetJson() {
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Test
	public void test_searchPostgres_postXmlGetXml() {
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	/*
	 * Tests for searchMariaDB endpoint
	 */
	@Ignore
	@Test
	public void test_searchMariaDB_postJsonGetJson() {
		Response response = client.target(uri).path(searchMariaDB).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMariaDB_postJsonGetXml() {
		Response response = client.target(uri).path(searchMariaDB).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMariaDB_postXmlGetJson() {
		Response response = client.target(uri).path(searchMariaDB).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMariaDB_postXmlGetXml() {
		Response response = client.target(uri).path(searchMariaDB).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	/*
	 * Tests for searchMySQL endpoint
	 */
	@Ignore
	@Test
	public void test_searchMySQL_postJsonGetJson() {
		Response response = client.target(uri).path(searchMySQL).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMySQL_postJsonGetXml() {
		Response response = client.target(uri).path(searchMySQL).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.json(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMySQL_postXmlGetJson() {
		Response response = client.target(uri).path(searchMySQL).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	@Ignore
	@Test
	public void test_searchMySQL_postXmlGetXml() {
		Response response = client.target(uri).path(searchMySQL).request().accept(MediaType.APPLICATION_XML)
				.post(Entity.xml(request));
		assertResponseWithDefaultBehaviour(response);
	}

	/*
	 * Test error conditions
	 */
	@Test
	public void test_serviceThrowsFastaResultParserException() {
		service.setBehaviour(r -> {
			throw new FastaResultParserException("abc");
		});
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("abc", errorMessage);
	}

	@Test
	public void test_serviceThrowsInvalidFastaSearchRequestException() {
		service.setBehaviour(r -> {
			throw new InvalidFastaSearchRequestException("def");
		});
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("def", errorMessage);
	}

	@Test
	public void test_serviceThrowsIOException() {
		service.setBehaviour(r -> {
			throw new IOException("ghi");
		});
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("ghi", errorMessage);
	}

	@Test
	public void test_serviceThrowsProgramExecutionException() {
		service.setBehaviour(r -> {
			throw new ProgramExecutionException("jkl");
		});
		Response response = client.target(uri).path(searchPostgres).request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(request));
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
		String errorMessage = response.readEntity(String.class);
		assertEquals("jkl", errorMessage);
	}
}
