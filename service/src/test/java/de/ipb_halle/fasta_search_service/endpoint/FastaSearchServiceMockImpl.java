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
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchRequest;
import de.ipb_halle.fasta_search_service.models.endpoint.FastaSearchResult;
import de.ipb_halle.fasta_search_service.search.LibraryFileFormat;
import de.ipb_halle.fasta_search_service.service.FastaSearchService;
import de.ipb_halle.fasta_search_service.service.InvalidFastaSearchRequestException;
import de.ipb_halle.fasta_search_service.service.ProgramExecutionException;

/**
 * @author flange
 */
@LocalBean
@Singleton
public class FastaSearchServiceMockImpl implements FastaSearchService {
	private FunctionWithExceptions<FastaSearchRequest, FastaSearchResult> behaviour;

	@Override
	public FastaSearchResult search(FastaSearchRequest request, LibraryFileFormat format)
			throws FastaResultParserException, InvalidFastaSearchRequestException, IOException,
			ProgramExecutionException {
		return behaviour.apply(request);
	}

	public void setBehaviour(FunctionWithExceptions<FastaSearchRequest, FastaSearchResult> behaviour) {
		this.behaviour = behaviour;
	}

	@FunctionalInterface
	public interface FunctionWithExceptions<T, R> {
		public R apply(T t) throws FastaResultParserException, InvalidFastaSearchRequestException, IOException,
				ProgramExecutionException;
	}
}