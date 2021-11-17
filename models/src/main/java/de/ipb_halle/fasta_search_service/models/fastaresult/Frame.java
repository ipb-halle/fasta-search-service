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
package de.ipb_halle.fasta_search_service.models.fastaresult;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Orientation of an alignment result, which has different meanings for each
 * program of the fasta36 suite.
 * <p>
 * What happens in the alignments in case frame is REVERSE?<p>
 * - Protein query -> Protein DB (fasta36): no reverse permitted<p>
 * - DNA query -> DNA DB (fasta36): query alignment is reverse complement, subject alignment is forward<p>
 * - DNA query -> Protein DB (fastx36/fasty36): query alignment is reverse complement, subject alignment is forward<p>
 * - Protein query -> DNA DB (tfastx36/tfasty36): query alignment is forward, subject alignment is reverse complement
 * 
 * @author flange
 */
public enum Frame {
	FORWARD("f"),
	REVERSE("r");

	private final String pattern;
	private static Map<String, Frame> lookupMap = Stream.of(values()).collect(toMap(e -> e.pattern, e -> e));

	private Frame(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @param pattern value of the frame direction from a fasta36 result output
	 * @return enumeration item matching the given pattern
	 * @throws FastaResultBuilderException if the pattern matches no item
	 */
	public static Frame fromPattern(String pattern) throws FastaResultBuilderException {
		Frame result = lookupMap.get(pattern);
		if (result == null) {
			throw new FastaResultBuilderException("Unknown pattern '" + pattern + "'.");
		}
		return result;
	}
}