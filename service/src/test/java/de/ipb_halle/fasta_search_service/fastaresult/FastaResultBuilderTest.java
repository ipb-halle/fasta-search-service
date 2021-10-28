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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaResultBuilderTest {
	@Test
	public void testDefaults() {
		FastaResultBuilder builder = FastaResult.builder();
		double delta = 0.0;

		assertNull(builder.getFrame());
		assertEquals(0.0d, builder.getBitScore(), delta);
		assertEquals(0.0d, builder.getExpectationValue(), delta);
		assertEquals(0, builder.getSmithWatermanScore());
		assertEquals(0.0d, builder.getIdentity(), delta);
		assertEquals(0.0d, builder.getSimilarity(), delta);
		assertEquals(0, builder.getOverlap());
		assertNull(builder.getQuerySequenceName());
		assertNull(builder.getQuerySequenceDescription());
		assertEquals(0, builder.getQuerySequenceLength());
		assertEquals(0, builder.getQueryAlignmentStart());
		assertEquals(0, builder.getQueryAlignmentStop());
		assertEquals(0, builder.getQueryAlignmentDisplayStart());
		assertNull(builder.getQueryAlignmentLine());
		assertNull(builder.getSubjectSequenceName());
		assertNull(builder.getSubjectSequenceDescription());
		assertEquals(0, builder.getSubjectSequenceLength());
		assertEquals(0, builder.getSubjectAlignmentStart());
		assertEquals(0, builder.getSubjectAlignmentStop());
		assertEquals(0, builder.getSubjectAlignmentDisplayStart());
		assertNull(builder.getSubjectAlignmentLine());
		assertNull(builder.getConsensusLine());
	}

	@Test
	public void testGettersAndSetters() {
		FastaResultBuilder builder;
		double delta = 0.001;

		builder = FastaResult.builder();
		assertSame(builder, builder.frame(Frame.REVERSE));
		assertEquals(Frame.REVERSE, builder.getFrame());

		builder = FastaResult.builder();
		assertSame(builder, builder.bitScore(123.456));
		assertEquals(123.456, builder.getBitScore(), delta);

		builder = FastaResult.builder();
		assertSame(builder, builder.expectationValue(2123.456));
		assertEquals(2123.456, builder.getExpectationValue(), delta);

		builder = FastaResult.builder();
		assertSame(builder, builder.smithWatermanScore(42));
		assertEquals(42, builder.getSmithWatermanScore());

		builder = FastaResult.builder();
		assertSame(builder, builder.identity(1323.456));
		assertEquals(1323.456, builder.getIdentity(), delta);

		builder = FastaResult.builder();
		assertSame(builder, builder.similarity(1253.456));
		assertEquals(1253.456, builder.getSimilarity(), delta);

		builder = FastaResult.builder();
		assertSame(builder, builder.overlap(43));
		assertEquals(43, builder.getOverlap());

		builder = FastaResult.builder();
		assertSame(builder, builder.querySequenceName("a"));
		assertEquals("a", builder.getQuerySequenceName());

		builder = FastaResult.builder();
		assertSame(builder, builder.querySequenceDescription("ab"));
		assertEquals("ab", builder.getQuerySequenceDescription());

		builder = FastaResult.builder();
		assertSame(builder, builder.querySequenceLength(125));
		assertEquals(125, builder.getQuerySequenceLength());

		builder = FastaResult.builder();
		assertSame(builder, builder.queryAlignmentStart(123455));
		assertEquals(123455, builder.getQueryAlignmentStart());

		builder = FastaResult.builder();
		assertSame(builder, builder.queryAlignmentStop(1255));
		assertEquals(1255, builder.getQueryAlignmentStop());

		builder = FastaResult.builder();
		assertSame(builder, builder.queryAlignmentDisplayStart(1575));
		assertEquals(1575, builder.getQueryAlignmentDisplayStart());

		builder = FastaResult.builder();
		assertSame(builder, builder.queryAlignmentLine("abcd"));
		assertEquals("abcd", builder.getQueryAlignmentLine());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectSequenceName("abcde"));
		assertEquals("abcde", builder.getSubjectSequenceName());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectSequenceDescription("abcdef"));
		assertEquals("abcdef", builder.getSubjectSequenceDescription());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectSequenceLength(987));
		assertEquals(987, builder.getSubjectSequenceLength());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectAlignmentStart(9876));
		assertEquals(9876, builder.getSubjectAlignmentStart());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectAlignmentStop(98760));
		assertEquals(98760, builder.getSubjectAlignmentStop());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectAlignmentDisplayStart(7601));
		assertEquals(7601, builder.getSubjectAlignmentDisplayStart());

		builder = FastaResult.builder();
		assertSame(builder, builder.subjectAlignmentLine("g"));
		assertEquals("g", builder.getSubjectAlignmentLine());

		builder = FastaResult.builder();
		assertSame(builder, builder.consensusLine("gh"));
		assertEquals("gh", builder.getConsensusLine());
	}

	@Test
	public void testBuildFails() {
		assertThrows(FastaResultParserException.class, () -> new FastaResultBuilder().build());
	}
}