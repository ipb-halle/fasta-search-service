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
package de.ipb_halle.fasta_search_service.fastaresult;

import java.util.HashMap;
import java.util.Map;

public enum Frame {
	FORWARD("f", "forward"),
	REVERSE("r", "reverse");

	private final String pattern;
	private final String name;
	private static Map<String, Frame> lookupMap = new HashMap<>();

	static {
		for (Frame f : values()) {
			lookupMap.put(f.pattern, f);
		}
	}

	private Frame(String pattern, String name) {
		this.pattern = pattern;
		this.name = name;
	}

	public static Frame fromPattern(String pattern) throws FastaResultParserException {
		Frame result = lookupMap.get(pattern);
		if (result == null) {
			throw new FastaResultParserException("Unknown pattern '" + pattern + "'.");
		}
		return result;
	}

	public String getName() {
		return name;
	}
}