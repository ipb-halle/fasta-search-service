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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaResultTest {
	@Test
	public void test_defaultsAndGettersAndSetters() {
		FastaResult result = new FastaResult();
		double delta = 0.001;

		assertNull(result.getFrame());
		result.setFrame(Frame.REVERSE);
		assertEquals(Frame.REVERSE, result.getFrame());

		assertEquals(0, result.getBitScore(), delta);
		result.setBitScore(123.456);
		assertEquals(123.456, result.getBitScore(), delta);

		assertEquals(0, result.getExpectationValue(), delta);
		result.setExpectationValue(2123.456);
		assertEquals(2123.456, result.getExpectationValue(), delta);

		assertEquals(0, result.getSmithWatermanScore());
		result.setSmithWatermanScore(42);
		assertEquals(42, result.getSmithWatermanScore());

		assertEquals(0, result.getIdentity(), delta);
		result.setIdentity(1323.456);
		assertEquals(1323.456, result.getIdentity(), delta);

		assertEquals(0, result.getSimilarity(), delta);
		result.setSimilarity(1253.456);
		assertEquals(1253.456, result.getSimilarity(), delta);

		assertEquals(0, result.getOverlap());
		result.setOverlap(43);
		assertEquals(43, result.getOverlap());

		assertNull(result.getQuerySequenceName());
		result.setQuerySequenceName("a");
		assertEquals("a", result.getQuerySequenceName());

		assertNull(result.getQuerySequenceDescription());
		result.setQuerySequenceDescription("ab");
		assertEquals("ab", result.getQuerySequenceDescription());

		assertEquals(0, result.getQuerySequenceLength());
		result.setQuerySequenceLength(125);
		assertEquals(125, result.getQuerySequenceLength());

		assertEquals(0, result.getQueryAlignmentStart());
		result.setQueryAlignmentStart(123455);
		assertEquals(123455, result.getQueryAlignmentStart());

		assertEquals(0, result.getQueryAlignmentStop());
		result.setQueryAlignmentStop(1255);
		assertEquals(1255, result.getQueryAlignmentStop());

		assertEquals(0, result.getQueryAlignmentDisplayStart());
		result.setQueryAlignmentDisplayStart(1575);
		assertEquals(1575, result.getQueryAlignmentDisplayStart());

		assertNull(result.getQueryAlignmentLine());
		result.setQueryAlignmentLine("abcd");
		assertEquals("abcd", result.getQueryAlignmentLine());

		assertNull(result.getSubjectSequenceName());
		result.setSubjectSequenceName("abcde");
		assertEquals("abcde", result.getSubjectSequenceName());

		assertNull(result.getSubjectSequenceDescription());
		result.setSubjectSequenceDescription("abcdef");
		assertEquals("abcdef", result.getSubjectSequenceDescription());

		assertEquals(0, result.getSubjectSequenceLength());
		result.setSubjectSequenceLength(987);
		assertEquals(987, result.getSubjectSequenceLength());

		assertEquals(0, result.getSubjectAlignmentStart());
		result.setSubjectAlignmentStart(9876);
		assertEquals(9876, result.getSubjectAlignmentStart());

		assertEquals(0, result.getSubjectAlignmentStop());
		result.setSubjectAlignmentStop(98760);
		assertEquals(98760, result.getSubjectAlignmentStop());

		assertEquals(0, result.getSubjectAlignmentDisplayStart());
		result.setSubjectAlignmentDisplayStart(7601);
		assertEquals(7601, result.getSubjectAlignmentDisplayStart());

		assertNull(result.getSubjectAlignmentLine());
		result.setSubjectAlignmentLine("g");
		assertEquals("g", result.getSubjectAlignmentLine());

		assertNull(result.getConsensusLine());
		result.setConsensusLine("gh");
		assertEquals("gh", result.getConsensusLine());
	}

	@Test
	public void test_build() throws FastaResultBuilderException {
		FastaResultBuilder builder = newBuilder();
		double delta = 0.001;

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

	@Test
	public void test_equalsAndHashCode() throws FastaResultBuilderException {
		FastaResultBuilder builder = newBuilder();
		FastaResult result1 = builder.build();
		FastaResult result2;

		result2 = newBuilder().build();
		assertEquals(result1, result2);
		assertEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().frame(Frame.FORWARD).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().bitScore(13.2).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().expectationValue(0.2).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().smithWatermanScore(1).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().identity(9.1).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().similarity(73.8).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().overlap(42).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().querySequenceName("b").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().querySequenceDescription("ba").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().querySequenceLength(2).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().queryAlignmentStart(57).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().queryAlignmentStop(84).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().queryAlignmentDisplayStart(35).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().queryAlignmentLine("xyz").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectSequenceName("qrs").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectSequenceDescription("abc").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectSequenceLength(23685).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectAlignmentStart(478).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectAlignmentStop(1237).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectAlignmentDisplayStart(234).build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().subjectAlignmentLine("h").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());

		result2 = newBuilder().consensusLine(":").build();
		assertNotEquals(result1, result2);
		assertNotEquals(result1.hashCode(), result2.hashCode());
	}

	private FastaResultBuilder newBuilder() {
		return FastaResult.builder()
				.frame(Frame.REVERSE)
				.bitScore(123.456)
				.expectationValue(2123.456)
				.smithWatermanScore(42)
				.identity(1323.456)
				.similarity(1253.456)
				.overlap(43)
				.querySequenceName("a")
				.querySequenceDescription("ab")
				.querySequenceLength(125)
				.queryAlignmentStart(123455)
				.queryAlignmentStop(1255)
				.queryAlignmentDisplayStart(1575)
				.queryAlignmentLine("abcd")
				.subjectSequenceName("abcde")
				.subjectSequenceDescription("abcdef")
				.subjectSequenceLength(987)
				.subjectAlignmentStart(9876)
				.subjectAlignmentStop(98760)
				.subjectAlignmentDisplayStart(7601)
				.subjectAlignmentLine("g")
				.consensusLine("gh");
	}
}