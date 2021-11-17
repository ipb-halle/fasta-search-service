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

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

//	@Inject
//	private Logger logger;

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
		//return handleRequest(request, LibraryFileFormat.MYSQL);
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
			throw new BadRequestException(e);
		} catch (IOException | FastaResultParserException | ProgramExecutionException e) {
			//logger.severe(ExceptionUtils.getStackTrace(e));
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}