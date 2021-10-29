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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.EnableServices;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchRequest;

/**
 * @author flange
 */
@EnableServices(value = "jaxrs", httpDebug = true)
@RunWith(ApplicationComposer.class)
public class FastaSearchEndpointTest {
	@Module
    @Classes({FastaSearchEndpoint.class, FastaSearchServiceMockImpl.class})
    public WebApp app() {
        return new WebApp().contextRoot("test");
    }

	@Test
	public void test() {
		FastaSearchQuery query = new FastaSearchQuery();
		query.setQuerySequence("AGCTGA");
		query.setQuerySequenceType("DNA");
		query.setLibrarySequenceType("PROTEIN");
		query.setTranslationTable(1);
		query.setMaxResults(10);
		FastaSearchRequest request = new FastaSearchRequest();
		request.setLibraryFile("mylibraryfile");
		request.setSearchQuery(query);

		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:4204/test").path("search").request()
				.accept(MediaType.APPLICATION_JSON).post(Entity.xml(request));

		System.out.println(response.toString());
		System.out.println(response.readEntity(FastaSearchRequest.class).toString());
		client.close();
	}
}