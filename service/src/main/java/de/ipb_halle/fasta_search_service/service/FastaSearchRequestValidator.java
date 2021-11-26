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
package de.ipb_halle.fasta_search_service.service;

import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchQuery;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;

/**
 * Utility class for validation of {@link FastaSearchRequest} objects.
 * 
 * @author flange
 */
public class FastaSearchRequestValidator {
	private FastaSearchRequestValidator() {
	}

	/**
	 * Validate the input object.
	 * 
	 * @param request
	 * @throws InvalidFastaSearchRequestException in case validation fails
	 */
	public static void validateRequestOrFail(FastaSearchRequest request) throws InvalidFastaSearchRequestException {
		if (request == null) {
			throw new InvalidFastaSearchRequestException("Invalid request.");
		}

		FastaSearchQuery searchQuery = request.getSearchQuery();
		if (searchQuery == null) {
			throw new InvalidFastaSearchRequestException("Missing searchQuery.");
		}
		checkQuerySequence(searchQuery.getQuerySequence());
		checkDatabaseQueries(request.getDatabaseQueries());
	}

	private static void checkQuerySequence(String querySequence) throws InvalidFastaSearchRequestException {
		if ((querySequence == null) || (querySequence.isEmpty())) {
			throw new InvalidFastaSearchRequestException("Missing query sequence.");
		}
	}

	private static void checkDatabaseQueries(String queries) throws InvalidFastaSearchRequestException {
		if ((queries == null) || (queries.isEmpty())) {
			throw new InvalidFastaSearchRequestException("Missing database queries.");
		}
	}
}