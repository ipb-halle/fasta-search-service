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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import de.ipb_halle.fasta_search_service.search.ProgramExecutor;
import de.ipb_halle.fasta_search_service.search.ProgramOutput;

/**
 * @author flange
 */
public class ProgramExecutorMock extends ProgramExecutor {
	private SupplierWithException<ProgramOutput> behaviour;
	private String libraryFileContent;
	private String querySequenceFileContent;

	@Override
	public ProgramOutput execute() throws IOException {
		readLibraryFile();
		readQuerySequenceFile();
		return behaviour.get();
	}

	private void readLibraryFile() throws IOException {
		// name of the library file should be in the last command
		String filename = getCommandList().get(getCommandList().size() - 1).split(" ")[0];
		libraryFileContent = readFile(filename);
	}

	private void readQuerySequenceFile() throws IOException {
		// name of the query sequence file should be in the next-to-last command
		String filename = getCommandList().get(getCommandList().size() - 2).split(" ")[0];
		querySequenceFileContent = readFile(filename);
	}

	private String readFile(String filename) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filename)));
	}

	@Override
	public List<String> getCommandList() {
		return super.getCommandList();
	}

	public void setBehaviour(SupplierWithException<ProgramOutput> behaviour) {
		this.behaviour = behaviour;
	}

	public String getLibraryFileContent() {
		return libraryFileContent;
	}

	public String getQuerySequenceFileContent() {
		return querySequenceFileContent;
	}

	public void reset() {
		behaviour = null;
		libraryFileContent = null;
		querySequenceFileContent = null;
		getCommandList().clear();
	}

	@FunctionalInterface
	public interface SupplierWithException<T> {
		public T get() throws IOException;
	}
}