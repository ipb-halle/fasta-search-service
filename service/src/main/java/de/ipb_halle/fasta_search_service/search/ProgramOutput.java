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

/**
 * Data class holding the output of a program execution.
 * 
 * @author flange
 */
public class ProgramOutput {
	private final int exitValue;
	private final String stdout;
	private final String stderr;

	public ProgramOutput(int exitValue, String stdout, String stderr) {
		this.exitValue = exitValue;
		this.stdout = stdout;
		this.stderr = stderr;
	}
	
	/**
	 * @return exit value, see {@link Process#exitValue()}
	 */
	public int getExitValue() {
		return exitValue;
	}

	/**
	 * @return standard output (stdout)
	 */
	public String getStdout() {
		return stdout;
	}

	/**
	 * @return standard error (stderr)
	 */
	public String getStderr() {
		return stderr;
	}
}