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

import jakarta.enterprise.inject.Produces;

import de.ipb_halle.fasta_search_service.search.ProgramExecutor;
import de.ipb_halle.fasta_search_service.search.ProgramExecutorFactory;

/**
 * CDI Producer for {@link ProgramExecutorFactory} objects that return one and the
 * same {@link ProgramExecutorMock} instance (singleton).
 * 
 * @author flange
 */
public class ProgramExecutorFactoryMockProducer {
	private static final ProgramExecutorMock mockInstance = new ProgramExecutorMock();

	@Produces
	public ProgramExecutorFactory produce() {
		return new ProgramExecutorFactory() {
			@Override
			public ProgramExecutor createNewInstance() {
				return mockInstance;
			}
		};
	}

	public static ProgramExecutorMock getMockinstance() {
		return mockInstance;
	}
}
