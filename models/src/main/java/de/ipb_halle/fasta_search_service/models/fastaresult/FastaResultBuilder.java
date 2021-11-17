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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

/**
 * Builder for {@link FastaResult} objects.
 * 
 * @author flange
 */
public class FastaResultBuilder {
	private Frame frame;
	private double bitScore;
	private double expectationValue;
	private int smithWatermanScore;
	private double identity;
	private double similarity;
	private int overlap;
	private String querySequenceName;
	private String querySequenceDescription;
	private int querySequenceLength;
	private int queryAlignmentStart;
	private int queryAlignmentStop;
	private int queryAlignmentDisplayStart;
	private String queryAlignmentLine;
	private String subjectSequenceName;
	private String subjectSequenceDescription;
	private int subjectSequenceLength;
	private int subjectAlignmentStart;
	private int subjectAlignmentStop;
	private int subjectAlignmentDisplayStart;
	private String subjectAlignmentLine;
	private String consensusLine;

	FastaResultBuilder() {
	}

	/**
	 * Build a {@link FastaResult} object from the current state of this builder and
	 * check its validity.
	 * 
	 * @return valid {@link FastaResult} object
	 * @throws FastaResultBuilderException when validation of the constructed
	 *                                    {@link FastaResult} object fails
	 */
	public FastaResult build() throws FastaResultBuilderException {
		FastaResult result = new FastaResult(this);

		// Validate using bean validation (JSR 349).
		Set<ConstraintViolation<FastaResult>> constraintViolations = Validation.buildDefaultValidatorFactory()
				.getValidator().validate(result);
		if (constraintViolations.size() > 0) {
			StringBuilder messages = new StringBuilder();
			messages.append("Unable to build a valid FASTA result:");
			constraintViolations.forEach(violation -> messages.append("\n")
					.append(violation.getPropertyPath() + ": " + violation.getMessage()));
			throw new FastaResultBuilderException(messages.toString());
		}

		return result;
	}

	public Frame getFrame() {
		return frame;
	}

	public double getBitScore() {
		return bitScore;
	}

	public double getExpectationValue() {
		return expectationValue;
	}

	public int getSmithWatermanScore() {
		return smithWatermanScore;
	}

	public double getIdentity() {
		return identity;
	}

	public double getSimilarity() {
		return similarity;
	}

	public int getOverlap() {
		return overlap;
	}

	public String getQuerySequenceName() {
		return querySequenceName;
	}

	public String getQuerySequenceDescription() {
		return querySequenceDescription;
	}

	public int getQuerySequenceLength() {
		return querySequenceLength;
	}

	public int getQueryAlignmentStart() {
		return queryAlignmentStart;
	}

	public int getQueryAlignmentStop() {
		return queryAlignmentStop;
	}

	public int getQueryAlignmentDisplayStart() {
		return queryAlignmentDisplayStart;
	}

	public String getQueryAlignmentLine() {
		return queryAlignmentLine;
	}

	public String getSubjectSequenceName() {
		return subjectSequenceName;
	}

	public String getSubjectSequenceDescription() {
		return subjectSequenceDescription;
	}

	public int getSubjectSequenceLength() {
		return subjectSequenceLength;
	}

	public int getSubjectAlignmentStart() {
		return subjectAlignmentStart;
	}

	public int getSubjectAlignmentStop() {
		return subjectAlignmentStop;
	}

	public int getSubjectAlignmentDisplayStart() {
		return subjectAlignmentDisplayStart;
	}

	public String getSubjectAlignmentLine() {
		return subjectAlignmentLine;
	}

	public String getConsensusLine() {
		return consensusLine;
	}

	public FastaResultBuilder frame(Frame frame) {
		this.frame = frame;
		return this;
	}

	public FastaResultBuilder bitScore(double bitScore) {
		this.bitScore = bitScore;
		return this;
	}

	public FastaResultBuilder expectationValue(double expectationValue) {
		this.expectationValue = expectationValue;
		return this;
	}

	public FastaResultBuilder smithWatermanScore(int smithWatermanScore) {
		this.smithWatermanScore = smithWatermanScore;
		return this;
	}

	public FastaResultBuilder identity(double identity) {
		this.identity = identity;
		return this;
	}

	public FastaResultBuilder similarity(double similarity) {
		this.similarity = similarity;
		return this;
	}

	public FastaResultBuilder overlap(int overlap) {
		this.overlap = overlap;
		return this;
	}

	public FastaResultBuilder querySequenceName(String querySequenceName) {
		this.querySequenceName = querySequenceName;
		return this;
	}

	public FastaResultBuilder querySequenceDescription(String querySequenceDescription) {
		this.querySequenceDescription = querySequenceDescription;
		return this;
	}

	public FastaResultBuilder querySequenceLength(int querySequenceLength) {
		this.querySequenceLength = querySequenceLength;
		return this;
	}

	public FastaResultBuilder queryAlignmentStart(int queryAlignmentStart) {
		this.queryAlignmentStart = queryAlignmentStart;
		return this;
	}

	public FastaResultBuilder queryAlignmentStop(int queryAlignmentStop) {
		this.queryAlignmentStop = queryAlignmentStop;
		return this;
	}

	public FastaResultBuilder queryAlignmentDisplayStart(int queryAlignmentDisplayStart) {
		this.queryAlignmentDisplayStart = queryAlignmentDisplayStart;
		return this;
	}

	public FastaResultBuilder queryAlignmentLine(String queryAlignmentLine) {
		this.queryAlignmentLine = queryAlignmentLine;
		return this;
	}

	public FastaResultBuilder subjectSequenceName(String subjectSequenceName) {
		this.subjectSequenceName = subjectSequenceName;
		return this;
	}

	public FastaResultBuilder subjectSequenceDescription(String subjectSequenceDescription) {
		this.subjectSequenceDescription = subjectSequenceDescription;
		return this;
	}

	public FastaResultBuilder subjectSequenceLength(int subjectSequenceLength) {
		this.subjectSequenceLength = subjectSequenceLength;
		return this;
	}

	public FastaResultBuilder subjectAlignmentStart(int subjectAlignmentStart) {
		this.subjectAlignmentStart = subjectAlignmentStart;
		return this;
	}

	public FastaResultBuilder subjectAlignmentStop(int subjectAlignmentStop) {
		this.subjectAlignmentStop = subjectAlignmentStop;
		return this;
	}

	public FastaResultBuilder subjectAlignmentDisplayStart(int subjectAlignmentDisplayStart) {
		this.subjectAlignmentDisplayStart = subjectAlignmentDisplayStart;
		return this;
	}

	public FastaResultBuilder subjectAlignmentLine(String subjectAlignmentLine) {
		this.subjectAlignmentLine = subjectAlignmentLine;
		return this;
	}

	public FastaResultBuilder consensusLine(String consensusLine) {
		this.consensusLine = consensusLine;
		return this;
	}
}