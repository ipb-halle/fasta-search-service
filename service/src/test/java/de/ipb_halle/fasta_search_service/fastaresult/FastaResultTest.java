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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * @author flange
 */
public class FastaResultTest {
	@Test
	public void test_build() throws FastaResultParserException {
		FastaResultBuilder builder = FastaResult.builder();
		double delta = 0.001;

		builder.frame(Frame.REVERSE);
		builder.bitScore(123.456);
		builder.expectationValue(2123.456);
		builder.smithWatermanScore(42);
		builder.identity(1323.456);
		builder.similarity(1253.456);
		builder.overlap(43);
		builder.querySequenceName("a");
		builder.querySequenceDescription("ab");
		builder.querySequenceLength(125);
		builder.queryAlignmentStart(123455);
		builder.queryAlignmentStop(1255);
		builder.queryAlignmentDisplayStart(1575);
		builder.queryAlignmentLine("abcd");
		builder.subjectSequenceName("abcde");
		builder.subjectSequenceDescription("abcdef");
		builder.subjectSequenceLength(987);
		builder.subjectAlignmentStart(9876);
		builder.subjectAlignmentStop(98760);
		builder.subjectAlignmentDisplayStart(7601);
		builder.subjectAlignmentLine("g");
		builder.consensusLine("gh");

		FastaResult result = builder.build();

		assertEquals(Frame.REVERSE, result.getFrame());
		assertEquals(123.456, result.getBitScore(), delta);
		assertEquals(2123.456, result.getExpectationValue(), delta);
		assertEquals(42, result.getSmithWatermanScore());
		assertEquals(1323.456, result.getIdentity(), delta);
		assertEquals(1253.456, result.getSimilarity(), delta);
		assertEquals(43, result.getOverlap());
		assertEquals("a", result.getQuerySequenceName());
		assertEquals("ab", result.getQuerySequenceDescription());
		assertEquals(125, result.getQuerySequenceLength());
		assertEquals(123455, result.getQueryAlignmentStart());
		assertEquals(1255, result.getQueryAlignmentStop());
		assertEquals(1575, result.getQueryAlignmentDisplayStart());
		assertEquals("abcd", result.getQueryAlignmentLine());
		assertEquals("abcde", result.getSubjectSequenceName());
		assertEquals("abcdef", result.getSubjectSequenceDescription());
		assertEquals(987, result.getSubjectSequenceLength());
		assertEquals(9876, result.getSubjectAlignmentStart());
		assertEquals(98760, result.getSubjectAlignmentStop());
		assertEquals(7601, result.getSubjectAlignmentDisplayStart());
		assertEquals("g", result.getSubjectAlignmentLine());
		assertEquals("gh", result.getConsensusLine());
	}
}