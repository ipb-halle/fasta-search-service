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

import java.io.IOException;
import java.util.List;

import de.ipb_halle.fasta_search_service.search.ProgramExecutor;
import de.ipb_halle.fasta_search_service.search.ProgramOutput;

/**
 * @author flange
 */
public class ProgramExecutorMock extends ProgramExecutor {
	private SupplierWithException<ProgramOutput> behaviour;

	@Override
	public ProgramOutput execute() throws IOException {
		return behaviour.get();
	}

	@Override
	public List<String> getCommandList() {
		return super.getCommandList();
	}

	public void setBehaviour(SupplierWithException<ProgramOutput> behaviour) {
		this.behaviour = behaviour;
	}

	@FunctionalInterface
	public interface SupplierWithException<T> {
		public T get() throws IOException;
	}
}