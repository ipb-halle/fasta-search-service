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
package de.ipb_halle.fasta_search_service_it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import de.ipb_halle.fasta_search_service.endpoint.model.FastaSearchResult;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResult;
import de.ipb_halle.fasta_search_service.fastaresult.Frame;

/**
 * Collection of reusable assertions.
 * 
 * @author flange
 */
public class FastaSearchResultAssertions {
	private FastaSearchResultAssertions() {
	}

	public static void assertDNASearchInDNALibraryResult(FastaSearchResult searchResult) {
		assertFalse(searchResult.getProgramOutput().isEmpty());

		List<FastaResult> fastaResults = searchResult.getResults();
		// this is flaky, because the fasta algorithm can deliver different number of results in each run
//		assertEquals(11, fastaResults.size());

		// copy-paste from FastaResultParserTest.testParseResults8()
		FastaResult result = fastaResults.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(0, result.getSmithWatermanScore());
		assertEquals(99, result.getOverlap());
		assertEquals(99, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(99, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("------------------------------CCTGCCGATCTGGTTAACTA"
				   + "CAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAAC"
				   + "TGAGCGCGGCCGCTTTGCAGTACAGCGAC", result.getQueryAlignmentLine());
		assertEquals("ENA|BAA28282|BAA28282.1", result.getSubjectSequenceName());
		assertEquals("Escherichia coli beta-lactamase", result.getSubjectSequenceDescription());
		assertEquals(870, result.getSubjectSequenceLength());
		assertEquals(304, result.getSubjectAlignmentStart());
		assertEquals(402, result.getSubjectAlignmentStop());
		assertEquals(274, result.getSubjectAlignmentDisplayStart());
		assertEquals("CAGCTGCTTAATCAGCCTGTCGAGATCAAGCCTGCCGATCTGGTTAACTA"
				   + "CAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAAC"
				   + "TGAGCGCGGCCGCTTTGCAGTACAGCGACAATACCGCCATGAACAAATTG"
				   + "ATTGCCCAGCTCGGTGGCCCGGGAGGCGTG", result.getSubjectAlignmentLine());
		assertEquals("                              ::::::::::::::::::::"
				   + "::::::::::::::::::::::::::::::::::::::::::::::::::"
				   + ":::::::::::::::::::::::::::::", result.getConsensusLine());
	}

	public static void assertDNASearchInProteinLibraryResult(FastaSearchResult searchResult) {
		assertFalse(searchResult.getProgramOutput().isEmpty());

		List<FastaResult> fastaResults = searchResult.getResults();

		// copy-paste from FastaResultParserTest.testParseResults9()
		FastaResult result = fastaResults.get(4);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(26, result.getOverlap());
		assertEquals(99, result.getQuerySequenceLength());
		assertEquals(22, result.getQueryAlignmentStart());
		assertEquals(99, result.getQueryAlignmentStop());
		assertEquals(22, result.getQueryAlignmentDisplayStart());
		assertEquals("NPIAEKHVNGTMTLAELSAAALQYSD", result.getQueryAlignmentLine());
		assertEquals("sp|Q03680|BLA1_STRCI", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase 1 OS=Streptomyces cacaoi OX=1898 GN=blaL PE=1 SV=1",
				result.getSubjectSequenceDescription());
		assertEquals(325, result.getSubjectSequenceLength());
		assertEquals(138, result.getSubjectAlignmentStart());
		assertEquals(163, result.getSubjectAlignmentStop());
		assertEquals(138, result.getSubjectAlignmentDisplayStart());
		assertEquals("SPVTEKHVADGMSLRELCDAVVAYSD", result.getSubjectAlignmentLine());
		assertEquals(".:..::::   :.: ::  :.. :::", result.getConsensusLine());
	}

	public static void assertProteinSearchInProteinLibraryResult(FastaSearchResult searchResult) {
		assertFalse(searchResult.getProgramOutput().isEmpty());

		List<FastaResult> fastaResults = searchResult.getResults();

		// copy-paste from FastaResultParserTest.testParseResults7()
		FastaResult result = fastaResults.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(50, result.getOverlap());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("------------------------------SAVQQKLAALEKSSGGRLGV"
				   + "ALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|O69395|BLT2_ECOLX", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase Toho-2 OS=Escherichia coli OX=562 GN=bla PE=3 SV=1", result.getSubjectSequenceDescription());
		assertEquals(289, result.getSubjectSequenceLength());
		assertEquals(31, result.getSubjectAlignmentStart());
		assertEquals(80, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MVTKRVQRMMSAAAACIPLLLGSPTLYAQTSAVQQKLAALEKSSGGRLGV"
				   + "ALIDTADNTQVLYRGDERFPMCSTSKVMAAAAVLKQSETQKQLLNQPVEI"
				   + "KPADLVNYNPIAEKHVNGTM", result.getSubjectAlignmentLine());
		assertEquals("                              ::::::::::::::::::::"
				   + "::::::::::::::::::::::::::::::", result.getConsensusLine());
	}

	public static void assertProteinSearchInDNALibraryResult(FastaSearchResult searchResult) {
		assertFalse(searchResult.getProgramOutput().isEmpty());

		List<FastaResult> fastaResults = searchResult.getResults();

		FastaResult result = fastaResults.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(50, result.getOverlap());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("ENA|BAA28282|BAA28282.1", result.getSubjectSequenceName());
		assertEquals("Escherichia coli beta-lactamase",	result.getSubjectSequenceDescription());
		assertEquals(870, result.getSubjectSequenceLength());
		assertEquals(91, result.getSubjectAlignmentStart());
		assertEquals(240, result.getSubjectAlignmentStop());
		assertEquals(91, result.getSubjectAlignmentDisplayStart());
		assertEquals("SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getSubjectAlignmentLine());
		assertEquals("::::::::::::::::::::::::::::::::::::::::::::::::::", result.getConsensusLine());
	}
}