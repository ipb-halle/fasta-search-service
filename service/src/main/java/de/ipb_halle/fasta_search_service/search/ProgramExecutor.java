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
package de.ipb_halle.fasta_search_service.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Executes a process according to the enqueued commands.
 * 
 * @author flange
 */
public class ProgramExecutor {
	private final List<String> commandList = new ArrayList<>();

	/**
	 * Enqueues one for more commands.
	 * 
	 * @param commands
	 * @return this object
	 */
	public ProgramExecutor addCommands(String... commands) {
		commandList.addAll(Arrays.asList(commands));
		return this;
	}

	List<String> getCommandList() {
		return commandList;
	}

	/**
	 * Execute a process according to the enqueued commands.
	 * 
	 * @return output of of the program execution (typically {@code stdout})
	 * @throws IOException
	 */
	public String execute() throws IOException {
		ProcessBuilder builder = new ProcessBuilder(commandList);
		Process process = builder.start();

		StringJoiner sj = new StringJoiner("\n");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sj.add(line);
			}
		}

		return sj.toString();
	}
}