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
package de.ipb_halle.fasta_search_service_it.util;

import java.nio.file.Paths;
import java.util.function.Consumer;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.images.builder.ImageFromDockerfile;

/**
 * Utility class with useful methods for tests.
 * 
 * @author flange
 */
public class TestUtils {
	private TestUtils() {
	}

	private static final String dbConnectionFormat = "%s:%d %s %s %s";
	private static final String selectAllSequencesFormat = "SELECT id,sequence FROM %s WHERE sequencetype = '%s';";
	private static final String selectDescriptionByIdFormat = "SELECT description FROM %s WHERE id=#;";
	private static final String selectSequenceByIdFormat = "SELECT sequence FROM %s WHERE id=#;";

	public static String getDatabaseConnectionString(String hostname, int port, String dbName, String user, String password) {
		return String.format(dbConnectionFormat, hostname, port, dbName, user, password);
	}

	public static String getDatabaseQueries(
			String tableName, String sequenceType) {
		StringBuilder sb = new StringBuilder(256);

		sb.append("DO SELECT 1;");
		sb.append("\n");
		sb.append(String.format(selectAllSequencesFormat, tableName, sequenceType));
		sb.append("\n");
		sb.append(String.format(selectDescriptionByIdFormat, tableName));
		sb.append("\n");
		sb.append(String.format(selectSequenceByIdFormat, tableName));

		return sb.toString();
	}

	@SuppressWarnings("deprecation")
	public static ImageFromDockerfile getTestbuildImage() {
		/*
		 * withDockerfile() is broken, see
		 * https://github.com/testcontainers/testcontainers-java/issues/3218
		 */
		return new ImageFromDockerfile().withDockerfilePath("Dockerfile").withFileFromPath(".",
				Paths.get("").toAbsolutePath().getParent());
	}

	@SuppressWarnings("resource")
	public static GenericContainer<?> getTestContainer(Network network, Consumer<OutputFrame> logConsumer) {
		return new GenericContainer<>(getTestbuildImage()).withExposedPorts(8080).withNetwork(network)
				.withLogConsumer(logConsumer);
	}
}