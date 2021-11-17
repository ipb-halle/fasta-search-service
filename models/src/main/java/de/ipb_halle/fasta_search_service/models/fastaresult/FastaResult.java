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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data class holding information of an alignment hit of a fasta search.
 * <p>
 * Use the {@link FastaResultBuilder} object supplied by {@link #builder()} to
 * construct an object of this class.
 * 
 * @author flange
 */
@XmlRootElement
public class FastaResult {
	/*
	 * fa_frame / sw_frame / fx_frame / tx_frame - frame direction
	 */
	@NotNull
	private Frame frame;

	/*
	 * fa_bits / sw_bits / fx_bits / tx_bits - bit score
	 */
	private double bitScore;

	/*
	 * fa_expect / sw_expect / fx_expect / tx_expect - E()-value
	 */
	private double expectationValue;

	/*
	 * sw_score - Smith-Waterman score (only in protein sequence comparisons)
	 */
	private int smithWatermanScore;

	/*
	 * sw_ident / bs_ident - identity
	 */
	private double identity;

	/*
	 * sw_sim / bs_sim - similarity (EBI FASTA calls this "positives")
	 */
	private double similarity;

	/*
	 * sw_overlap / bs_overlap - overlap
	 */
	private int overlap;

	/*
	 * Name of the query sequence
	 */
	@NotNull
	private String querySequenceName;

	/*
	 * Description of the query sequence
	 */
	@NotNull
	private String querySequenceDescription;

	/*
	 * sq_len - sequence length of the query sequence
	 */
	@Min(1)
	private int querySequenceLength;

	/*
	 * al_start - alignment start position in the query sequence
	 */
	@Min(1)
	private int queryAlignmentStart;

	/*
	 * al_stop - alignment stop position in the query sequence
	 */
	@Min(1)
	private int queryAlignmentStop;

	/*
	 * al_display_start - alignment display start position in the query sequence
	 */
	@Min(1)
	private int queryAlignmentDisplayStart;

	/*
	 * Query sequence alignment
	 */
	@NotNull
	private String queryAlignmentLine;

	/*
	 * Name of the subject sequence
	 */
	@NotNull
	private String subjectSequenceName;

	/*
	 * Description of the subject sequence
	 */
	@NotNull
	private String subjectSequenceDescription;

	/*
	 * sq_len - sequence length of the subject sequence
	 */
	@Min(1)
	private int subjectSequenceLength;

	/*
	 * al_start - alignment start position in the subject sequence
	 */
	@Min(1)
	private int subjectAlignmentStart;

	/*
	 * al_stop - alignment stop position in the subject sequence
	 */
	@Min(1)
	private int subjectAlignmentStop;

	/*
	 * al_display_start - alignment display start position in the subject sequence
	 */
	@Min(1)
	private int subjectAlignmentDisplayStart;

	/*
	 * Subject sequence alignment
	 */
	@NotNull
	private String subjectAlignmentLine;

	/*
	 * Consensus sequence
	 */
	@NotNull
	private String consensusLine;

	/*
	 * Getters and setters
	 */
	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public double getBitScore() {
		return bitScore;
	}

	public void setBitScore(double bitScore) {
		this.bitScore = bitScore;
	}

	public double getExpectationValue() {
		return expectationValue;
	}

	public void setExpectationValue(double expectationValue) {
		this.expectationValue = expectationValue;
	}

	public int getSmithWatermanScore() {
		return smithWatermanScore;
	}

	public void setSmithWatermanScore(int smithWatermanScore) {
		this.smithWatermanScore = smithWatermanScore;
	}

	public double getIdentity() {
		return identity;
	}

	public void setIdentity(double identity) {
		this.identity = identity;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public int getOverlap() {
		return overlap;
	}

	public void setOverlap(int overlap) {
		this.overlap = overlap;
	}

	public String getQuerySequenceName() {
		return querySequenceName;
	}

	public void setQuerySequenceName(String querySequenceName) {
		this.querySequenceName = querySequenceName;
	}

	public String getQuerySequenceDescription() {
		return querySequenceDescription;
	}

	public void setQuerySequenceDescription(String querySequenceDescription) {
		this.querySequenceDescription = querySequenceDescription;
	}

	public int getQuerySequenceLength() {
		return querySequenceLength;
	}

	public void setQuerySequenceLength(int querySequenceLength) {
		this.querySequenceLength = querySequenceLength;
	}

	public int getQueryAlignmentStart() {
		return queryAlignmentStart;
	}

	public void setQueryAlignmentStart(int queryAlignmentStart) {
		this.queryAlignmentStart = queryAlignmentStart;
	}

	public int getQueryAlignmentStop() {
		return queryAlignmentStop;
	}

	public void setQueryAlignmentStop(int queryAlignmentStop) {
		this.queryAlignmentStop = queryAlignmentStop;
	}

	public int getQueryAlignmentDisplayStart() {
		return queryAlignmentDisplayStart;
	}

	public void setQueryAlignmentDisplayStart(int queryAlignmentDisplayStart) {
		this.queryAlignmentDisplayStart = queryAlignmentDisplayStart;
	}

	public String getQueryAlignmentLine() {
		return queryAlignmentLine;
	}

	public void setQueryAlignmentLine(String queryAlignmentLine) {
		this.queryAlignmentLine = queryAlignmentLine;
	}

	public String getSubjectSequenceName() {
		return subjectSequenceName;
	}

	public void setSubjectSequenceName(String subjectSequenceName) {
		this.subjectSequenceName = subjectSequenceName;
	}

	public String getSubjectSequenceDescription() {
		return subjectSequenceDescription;
	}

	public void setSubjectSequenceDescription(String subjectSequenceDescription) {
		this.subjectSequenceDescription = subjectSequenceDescription;
	}

	public int getSubjectSequenceLength() {
		return subjectSequenceLength;
	}

	public void setSubjectSequenceLength(int subjectSequenceLength) {
		this.subjectSequenceLength = subjectSequenceLength;
	}

	public int getSubjectAlignmentStart() {
		return subjectAlignmentStart;
	}

	public void setSubjectAlignmentStart(int subjectAlignmentStart) {
		this.subjectAlignmentStart = subjectAlignmentStart;
	}

	public int getSubjectAlignmentStop() {
		return subjectAlignmentStop;
	}

	public void setSubjectAlignmentStop(int subjectAlignmentStop) {
		this.subjectAlignmentStop = subjectAlignmentStop;
	}

	public int getSubjectAlignmentDisplayStart() {
		return subjectAlignmentDisplayStart;
	}

	public void setSubjectAlignmentDisplayStart(int subjectAlignmentDisplayStart) {
		this.subjectAlignmentDisplayStart = subjectAlignmentDisplayStart;
	}

	public String getSubjectAlignmentLine() {
		return subjectAlignmentLine;
	}

	public void setSubjectAlignmentLine(String subjectAlignmentLine) {
		this.subjectAlignmentLine = subjectAlignmentLine;
	}

	public String getConsensusLine() {
		return consensusLine;
	}

	public void setConsensusLine(String consensusLine) {
		this.consensusLine = consensusLine;
	}

	/**
	 * Factory method that supplies a builder for this class.
	 * 
	 * @return builder
	 */
	public static FastaResultBuilder builder() {
		return new FastaResultBuilder();
	}

	FastaResult(FastaResultBuilder builder) {
		this.frame = builder.getFrame();
		this.bitScore = builder.getBitScore();
		this.expectationValue = builder.getExpectationValue();
		this.smithWatermanScore = builder.getSmithWatermanScore();
		this.identity = builder.getIdentity();
		this.similarity = builder.getSimilarity();
		this.overlap = builder.getOverlap();
		this.querySequenceName = builder.getQuerySequenceName();
		this.querySequenceDescription = builder.getQuerySequenceDescription();
		this.querySequenceLength = builder.getQuerySequenceLength();
		this.queryAlignmentStart = builder.getQueryAlignmentStart();
		this.queryAlignmentStop = builder.getQueryAlignmentStop();
		this.queryAlignmentDisplayStart = builder.getQueryAlignmentDisplayStart();
		this.queryAlignmentLine = builder.getQueryAlignmentLine();
		this.subjectSequenceName = builder.getSubjectSequenceName();
		this.subjectSequenceDescription = builder.getSubjectSequenceDescription();
		this.subjectSequenceLength = builder.getSubjectSequenceLength();
		this.subjectAlignmentStart = builder.getSubjectAlignmentStart();
		this.subjectAlignmentStop = builder.getSubjectAlignmentStop();
		this.subjectAlignmentDisplayStart = builder.getSubjectAlignmentDisplayStart();
		this.subjectAlignmentLine = builder.getSubjectAlignmentLine();
		this.consensusLine = builder.getConsensusLine();
	}

	public FastaResult() {
	}

	@Override
	public String toString() {
		return "FastaResult ["
				+ "\n  frame=" + frame
				+ "\n, bitScore=" + bitScore
				+ "\n, expectationValue=" + expectationValue
				+ "\n, smithWatermanScore=" + smithWatermanScore
				+ "\n, identity=" + identity
				+ "\n, similarity=" + similarity
				+ "\n, overlap=" + overlap
				+ "\n, querySequenceName=" + querySequenceName
				+ "\n, querySequenceDescription=" + querySequenceDescription
				+ "\n, querySequenceLength=" + querySequenceLength
				+ "\n, queryAlignmentStart=" + queryAlignmentStart
				+ "\n, queryAlignmentStop=" + queryAlignmentStop
				+ "\n, queryAlignmentDisplayStart=" + queryAlignmentDisplayStart
				+ "\n, queryAlignmentLine=" + queryAlignmentLine
				+ "\n, subjectSequenceName=" + subjectSequenceName
				+ "\n, subjectSequenceDescription=" + subjectSequenceDescription
				+ "\n, subjectSequenceLength=" + subjectSequenceLength
				+ "\n, subjectAlignmentStart=" + subjectAlignmentStart
				+ "\n, subjectAlignmentStop=" + subjectAlignmentStop
				+ "\n, subjectAlignmentDisplayStart=" + subjectAlignmentDisplayStart
				+ "\n, subjectAlignmentLine=" + subjectAlignmentLine
				+ "\n, consensusLine=" + consensusLine
				+ "\n]";
	}
}