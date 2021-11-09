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
package de.ipb_halle.fasta_search_service.util;

import java.util.regex.Pattern;

/**
 * Utility class that copes with the
 * <a href="https://en.wikipedia.org/wiki/FASTA_format">FASTA file format</a>.
 * 
 * @author flange
 */
public class FastaFileFormatUtils {
	private static final Pattern fastaHeaderRegex = Pattern.compile(">.*(\r|\n|\r\n).*", Pattern.DOTALL);
	private static final Pattern startsWithNewlineRegex = Pattern.compile("(\r|\n|\r\n).*", Pattern.DOTALL);

	private FastaFileFormatUtils() {
	}

	/**
	 * Transforms incoming text input into the FASTA file format by adding a header
	 * if necessary.
	 * 
	 * @param input
	 * @return input in FASTA file format
	 */
	public static String toFastaFileFormat(String input) {
		if (isInFastaFormat(input)) {
			return input;
		} else {
			return withFastaHeader(input);
		}
	}

	private static boolean isInFastaFormat(String input) {
		return fastaHeaderRegex.matcher(input).matches();
	}

	private static String withFastaHeader(String input) {
		if (startsWithNewlineRegex.matcher(input).matches()) {
			return ">query" + input;
		} else {
			return ">query\n" + input;
		}
	}
}