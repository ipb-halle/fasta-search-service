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
 * Format of library files used by the fasta36 program suite.
 * 
 * @author flange
 */
public enum LibraryFileFormat {
	MYSQL(16, "/usr/local/fasta36_mariadb/bin"),
	POSTGRES(17, "/usr/local/fasta36_postgresql/bin");

	private final int number;
	private final String binDirectory;

	private LibraryFileFormat(int number, String binDirectory) {
		this.number = number;
		this.binDirectory = binDirectory;
	}

	/**
	 * @return numerical ID of the library file format
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return directory with the fasta36 binaries (absolute path)
	 */
	public String getBinDirectory() {
		return binDirectory;
	}
}