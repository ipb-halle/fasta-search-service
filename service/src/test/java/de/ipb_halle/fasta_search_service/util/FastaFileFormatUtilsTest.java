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

import static de.ipb_halle.fasta_search_service.util.FastaFileFormatUtils.toFastaFileFormat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaFileFormatUtilsTest {
	@Test
	public void test_inFastaFormat() {
		String input, expected;

		input = "ABCDEF";
		expected = ">query\nABCDEF";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF";
		expected = ">query\n>ABCDEF";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF\n";
		expected = ">ABCDEF\n";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF\nGHI";
		expected = ">ABCDEF\nGHI";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF\rGHI";
		expected = ">ABCDEF\rGHI";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF\r\nGHI";
		expected = ">ABCDEF\r\nGHI";
		assertEquals(expected, toFastaFileFormat(input));

		input = ">ABCDEF\nGHI\nJKL";
		expected = ">ABCDEF\nGHI\nJKL";
		assertEquals(expected, toFastaFileFormat(input));

		input = "ABCDEF\nGHI\nJKL";
		expected = ">query\nABCDEF\nGHI\nJKL";
		assertEquals(expected, toFastaFileFormat(input));

		input = "\nABCDEF\nGHI";
		expected = ">query\nABCDEF\nGHI";
		assertEquals(expected, toFastaFileFormat(input));

		input = "\rABCDEF\rGHI";
		expected = ">query\rABCDEF\rGHI";
		assertEquals(expected, toFastaFileFormat(input));

		input = "\r\nABCDEF\r\nGHI";
		expected = ">query\r\nABCDEF\r\nGHI";
		assertEquals(expected, toFastaFileFormat(input));
	}
}