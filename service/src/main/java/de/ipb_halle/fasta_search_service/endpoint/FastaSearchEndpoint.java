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

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;

import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;
import de.ipb_halle.fasta_search_service.service.FastaSearchService;
import de.ipb_halle.fasta_search_service.service.InvalidFastaSearchRequestException;
import de.ipb_halle.fasta_search_service.service.ProgramExecutionException;

/**
 * REST API endpoints for fasta library searches.
 * 
 * @author flange
 */
@Path("/")
public class FastaSearchEndpoint {
	@Inject
	private FastaSearchService service;

	@Inject
	private Logger logger;

	@POST
	@Path("searchPostgres")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response searchPostgres(FastaSearchRequest request) {
		return handleRequest(request, LibraryFileFormat.POSTGRES);
	}

	@POST
	@Path("searchMariaDB")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response searchMariaDB(FastaSearchRequest request) {
		throw new ServiceUnavailableException("MariaDB searches are not working.");
		//return handleRequest(request, LibraryFileFormat.MARIADB);
	}

	@POST
	@Path("searchMySQL")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response searchMySQL(FastaSearchRequest request) {
		throw new ServiceUnavailableException("MySQL searches are not working.");
		//return handleRequest(request, LibraryFileFormat.MYSQL);
	}

	private Response handleRequest(FastaSearchRequest request, LibraryFileFormat format) {
		try {
			FastaSearchResult result = service.search(request, format);
			return Response.ok(result).build();
		} catch (InvalidFastaSearchRequestException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		} catch (FastaResultParserException | IOException | ProgramExecutionException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
