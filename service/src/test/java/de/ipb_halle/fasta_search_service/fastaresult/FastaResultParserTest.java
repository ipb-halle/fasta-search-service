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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.Test;

/**
 * @author flange
 */
public class FastaResultParserTest {
	// relative error in tests with double values
	private static final double delta = 0.001d;

	@Test
	public void testParseResults1() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results1.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(4, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(99.1d, r = result.getBitScore(), r * delta);
		assertEquals(8.7e-26d, r = result.getExpectationValue(), r * delta);
		assertEquals(313, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(50, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("------------------------------SAVQQKLAALEKSSGGRLGV"
		           + "ALIDTADNTQVLYRGDERFPMCSTSKVMAA",
				result.getQueryAlignmentLine());
		assertEquals("gb|AAF72530.1|AF252622_1", result.getSubjectSequenceName());
		assertEquals("beta-lactamase CTX-M-14 (plasmid) [Escherichia coli]",
				result.getSubjectSequenceDescription());
		assertEquals(291, result.getSubjectSequenceLength());
		assertEquals(31, result.getSubjectAlignmentStart());
		assertEquals(80, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MVTKRVQRMMFAAAACIPLLLGSAPLYAQTSAVQQKLAALEKSSGGRLGV"
				   + "ALIDTADNTQVLYRGDERFPMCSTSKVMAAAAVLKQSETQKQLLNQPVEI"
				   + "KPADLVNYNPIAEKHVNGTM", result.getSubjectAlignmentLine());
		assertEquals("                              ::::::::::::::::::::"
				   + "::::::::::::::::::::::::::::::",
				result.getConsensusLine());

		result = results.get(1);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(36.7d, r = result.getBitScore(), r * delta);
		assertEquals(5e-07d, r = result.getExpectationValue(), r * delta);
		assertEquals(100, result.getSmithWatermanScore());
		assertEquals(0.400d, r = result.getIdentity(), r * delta);
		assertEquals(0.733d, r = result.getSimilarity(), r * delta);
		assertEquals(45, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(5, result.getQueryAlignmentStart());
		assertEquals(48, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("----------------------SAVQQKLAALEKSSGGRLGVALIDTADN"
				   + "-TQVLYRGDERFPMCSTSKVMAA",
				result.getQueryAlignmentLine());
		assertEquals("gb|AAP20890.1|", result.getSubjectSequenceName());
		assertEquals("extended-spectrum beta-lactamase SHV-48 [Acinetobacter baumannii]",
				result.getSubjectSequenceDescription());
		assertEquals(286, result.getSubjectSequenceLength());
		assertEquals(27, result.getSubjectAlignmentStart());
		assertEquals(71, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MRYIRLCIISLLATLPLAVHASPQPLEQIKQSESQLSGRVGMIEMDLASG"
				   + "RTLTAWRADERFPMMSTFKVVLCGAVLARVDAGDEQLERKIHYRQQDLVD"
				   + "YSPVSEKHLADGMTVGELCA",
				result.getSubjectAlignmentLine());
		assertEquals("                          ...   :.. .::.:.  .: :.."
				   + "-: . .:.:::::: :: ::.",
				result.getConsensusLine());

		result = results.get(2);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(36.7d, r = result.getBitScore(), r * delta);
		assertEquals(5e-07d, r = result.getExpectationValue(), r * delta);
		assertEquals(100, result.getSmithWatermanScore());
		assertEquals(0.400d, r = result.getIdentity(), r * delta);
		assertEquals(0.733d, r = result.getSimilarity(), r * delta);
		assertEquals(45, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(5, result.getQueryAlignmentStart());
		assertEquals(48, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("----------------------SAVQQKLAALEKSSGGRLGVALIDTADN"
				   + "-TQVLYRGDERFPMCSTSKVMAA",
				result.getQueryAlignmentLine());
		assertEquals("gb|AAP20889.1|", result.getSubjectSequenceName());
		assertEquals("extended-spectrum beta-lactamase SHV-12 [Acinetobacter baumannii]",
				result.getSubjectSequenceDescription());
		assertEquals(286, result.getSubjectSequenceLength());
		assertEquals(27, result.getSubjectAlignmentStart());
		assertEquals(71, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MRYIRLCIISLLATLPLAVHASPQPLEQIKQSESQLSGRVGMIEMDLASG"
				   + "RTLTAWRADERFPMMSTFKVVLCGAVLARVDAGDEQLERKIHYRQQDLVD"
				   + "YSPVSEKHLADGMTVGELCA",
				result.getSubjectAlignmentLine());
		assertEquals("                          ...   :.. .::.:.  .: :.."
				   + "-: . .:.:::::: :: ::.",
				result.getConsensusLine());

		result = results.get(3);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(15.1d, r = result.getBitScore(), r * delta);
		assertEquals(0.73d, r = result.getExpectationValue(), r * delta);
		assertEquals(33, result.getSmithWatermanScore());
		assertEquals(0.417d, r = result.getIdentity(), r * delta);
		assertEquals(0.583d, r = result.getSimilarity(), r * delta);
		assertEquals(24, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(15, result.getQueryAlignmentStart());
		assertEquals(35, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("----------------SAVQQKLAALEKSSGGRLGVALI---DTADNTQV"
				   + "LYRGDERFPMCSTSKVMAA",
				result.getQueryAlignmentLine());
		assertEquals("gb|AAK07468.1|", result.getSubjectSequenceName());
		assertEquals("SHV beta-lactamase, partial [Escherichia coli]", result.getSubjectSequenceDescription());
		assertEquals(139, result.getSubjectSequenceLength());
		assertEquals(115, result.getSubjectAlignmentStart());
		assertEquals(138, result.getSubjectAlignmentStop());
		assertEquals(85, result.getSubjectAlignmentDisplayStart());
		assertEquals("MVDDRVAGPLIRSVLPAGWFIADKTGASKRGARGIVALLGPNNKAERIVV"
				   + "LYIGX", result.getSubjectAlignmentLine());
		assertEquals("                              :.:  :::.---. :.   :"
				   + ":: :", result.getConsensusLine());
	}

	@Test
	public void testParseResults2() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results2.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(1, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(6.5d, r = result.getBitScore(), r * delta);
		assertEquals(0.72d, r = result.getExpectationValue(), r * delta);
		assertEquals(13, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(1, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("", result.getQuerySequenceDescription());
		assertEquals(3, result.getQuerySequenceLength());
		assertEquals(3, result.getQueryAlignmentStart());
		assertEquals(3, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("----------------------------ABC",	result.getQueryAlignmentLine());
		assertEquals("seq1", result.getSubjectSequenceName());
		assertEquals("", result.getSubjectSequenceDescription());
		assertEquals(39, result.getSubjectSequenceLength());
		assertEquals(31, result.getSubjectAlignmentStart());
		assertEquals(31, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("KSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getSubjectAlignmentLine());
		assertEquals("                              :",	result.getConsensusLine());
	}

	@Test
	public void testParseResults3() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results3.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(1, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(6.3d, r = result.getBitScore(), r * delta);
		assertEquals(0.76d, r = result.getExpectationValue(), r * delta);
		assertEquals(13, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(1, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("", result.getQuerySequenceDescription());
		assertEquals(39, result.getQuerySequenceLength());
		assertEquals(31, result.getQueryAlignmentStart());
		assertEquals(31, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("KSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("seq1", result.getSubjectSequenceName());
		assertEquals("", result.getSubjectSequenceDescription());
		assertEquals(3, result.getSubjectSequenceLength());
		assertEquals(3, result.getSubjectAlignmentStart());
		assertEquals(3, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("----------------------------ABC", result.getSubjectAlignmentLine());
		assertEquals("                              :",	result.getConsensusLine());
	}

	@Test
	public void testParseResults4() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results4.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(1, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(6.5d, r = result.getBitScore(), r * delta);
		assertEquals(0.73d, r = result.getExpectationValue(), r * delta);
		assertEquals(13, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(1, result.getOverlap());
		assertEquals("", result.getQuerySequenceName());
		assertEquals("", result.getQuerySequenceDescription());
		assertEquals(3, result.getQuerySequenceLength());
		assertEquals(3, result.getQueryAlignmentStart());
		assertEquals(3, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("----------------------------ABC", result.getQueryAlignmentLine());
		assertEquals(">data4.fasta", result.getSubjectSequenceName());
		assertEquals("", result.getSubjectSequenceDescription());
		assertEquals(39, result.getSubjectSequenceLength());
		assertEquals(31, result.getSubjectAlignmentStart());
		assertEquals(31, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("KSSGGRLGVALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getSubjectAlignmentLine());
		assertEquals("                              :",	result.getConsensusLine());
	}

	@Test
	public void testParseResults5() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results5.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();

		assertEquals(0, results.size());
	}

	@Test
	public void testParseResults6() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results6.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();

		assertEquals(0, results.size());
	}

	@Test
	public void testParseResults7() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results7.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(9, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(109.3d, r = result.getBitScore(), r * delta);
		assertEquals(1.2e-28d, r = result.getExpectationValue(), r * delta);
		assertEquals(313, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(50, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
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

		result = results.get(1);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(90.9d, r = result.getBitScore(), r * delta);
		assertEquals(4.4e-23d, r = result.getExpectationValue(), r * delta);
		assertEquals(256, result.getSmithWatermanScore());
		assertEquals(0.800d, r = result.getIdentity(), r * delta);
		assertEquals(0.940d, r = result.getSimilarity(), r * delta);
		assertEquals(50, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("------------------------------SAVQQKLAALEKSSGGRLGV"
				   + "ALIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|O65976|BLC6_SALTM", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase CTX-M-6 OS=Salmonella typhimurium OX=90371 GN=bla PE=3 SV=1", result.getSubjectSequenceDescription());
		assertEquals(291, result.getSubjectSequenceLength());
		assertEquals(31, result.getSubjectAlignmentStart());
		assertEquals(80, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MMTQSIRRSMLTVMATLPLLFSSATLHAQANSVQQQLEALEKSSGGRLGV"
				   + "ALINTADNSQILYVADERFAMCSTSKVMAAAAVLKQSESDKHLLNQRVEI"
				   + "RASDLVNYNPIAEKHVNGTM", result.getSubjectAlignmentLine());
		assertEquals("                              ..:::.: ::::::::::::"
				   + ":::.::::.:.:: .:::: ::::::::::", result.getConsensusLine());

		result = results.get(2);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(49.5d, r = result.getBitScore(), r * delta);
		assertEquals(1.4e-10d, r = result.getExpectationValue(), r * delta);
		assertEquals(128, result.getSmithWatermanScore());
		assertEquals(0.449d, r = result.getIdentity(), r * delta);
		assertEquals(0.735d, r = result.getSimilarity(), r * delta);
		assertEquals(49, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(2, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("-----------------------------SAVQQKLAALEKSSGGRLGVA"
				   + "LIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|P00809|BLAC_BACCE", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase 1 OS=Bacillus cereus OX=1396 GN=blaY PE=3 SV=1", result.getSubjectSequenceDescription());
		assertEquals(306, result.getSubjectSequenceLength());
		assertEquals(50, result.getSubjectAlignmentStart());
		assertEquals(98, result.getSubjectAlignmentStop());
		assertEquals(20, result.getSubjectAlignmentDisplayStart());
		assertEquals("LSITSLEAFTGESLQVEAKEKTGQVKHKNQATHKEFSQLEKKFDARLGVY"
				   + "AIDTGTNQTISYRPNERFAFASTYKALAAGVLLQQNSIDSLNEVITYTKE"
				   + "DLVDYSPVTEKHVDTGMKLG", result.getSubjectAlignmentLine());
		assertEquals("                              :...... :::.  .:::: "
				   + " :::. :  . :: .::: . :: :..::", result.getConsensusLine());

		result = results.get(3);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(19.4d, r = result.getBitScore(), r * delta);
		assertEquals(0.16d, r = result.getExpectationValue(), r * delta);
		assertEquals(35, result.getSmithWatermanScore());
		assertEquals(0.357d, r = result.getIdentity(), r * delta);
		assertEquals(0.786d, r = result.getSimilarity(), r * delta);
		assertEquals(14, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(16, result.getQueryAlignmentStart());
		assertEquals(29, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("---------------SAVQQKLAALEKSSGGRLGVALIDTADNTQVLYRG"
				   + "DERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|P00809|BLAC_BACCE", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase 1 OS=Bacillus cereus OX=1396 GN=blaY PE=3 SV=1", result.getSubjectSequenceDescription());
		assertEquals(306, result.getSubjectSequenceLength());
		assertEquals(139, result.getSubjectAlignmentStart());
		assertEquals(152, result.getSubjectAlignmentStop());
		assertEquals(109, result.getSubjectAlignmentDisplayStart());
		assertEquals("SLNEVITYTKEDLVDYSPVTEKHVDTGMKLGEIAEAAVRSSDNTAGNILF"
				   + "NKIGGPKGYEKALRHMGDRITMSNRFETELNEAIPGDIRDTSTAKAIATN"
				   + "LKAFTVGNALPAEKRKILTE", result.getSubjectAlignmentLine());
		assertEquals("                              :... : . ..:::", result.getConsensusLine());

		result = results.get(4);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(43.6d, r = result.getBitScore(), r * delta);
		assertEquals(8.3e-09d, r = result.getExpectationValue(), r * delta);
		assertEquals(110, result.getSmithWatermanScore());
		assertEquals(0.408d, r = result.getIdentity(), r * delta);
		assertEquals(0.714d, r = result.getSimilarity(), r * delta);
		assertEquals(49, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(2, result.getQueryAlignmentStart());
		assertEquals(50, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("-----------------------------SAVQQKLAALEKSSGGRLGVA"
				   + "LIDTADNTQVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|Q03680|BLA1_STRCI", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase 1 OS=Streptomyces cacaoi OX=1898 GN=blaL PE=1 SV=1", result.getSubjectSequenceDescription());
		assertEquals(325, result.getSubjectSequenceLength());
		assertEquals(52, result.getSubjectAlignmentStart());
		assertEquals(100, result.getSubjectAlignmentStop());
		assertEquals(22, result.getSubjectAlignmentDisplayStart());
		assertEquals("LVACGQASGSESGQQPGLGGCGTSAHGSADAHEKEFRALEKKFDAHPGVY"
				   + "AIDTRDGQEITHRADERFAYGSTFKALQAGAILAQVLRDGREVRRGAEAD"
				   + "GMDKVVHYGQDAILPNSPVT", result.getSubjectAlignmentLine());
		assertEquals("                              : .... ::::.  .. :: "
				   + " ::: :. .. .:.:::: . :: :.. :", result.getConsensusLine());

		result = results.get(5);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(20.3d, r = result.getBitScore(), r * delta);
		assertEquals(0.085d, r = result.getExpectationValue(), r * delta);
		assertEquals(38, result.getSmithWatermanScore());
		assertEquals(0.700d, r = result.getIdentity(), r * delta);
		assertEquals(0.800d, r = result.getSimilarity(), r * delta);
		assertEquals(10, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(14, result.getQueryAlignmentStart());
		assertEquals(23, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("-----------------SAVQQKLAALEKSSGGRLGVALIDTADNTQVLY"
				   + "RGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|Q03680|BLA1_STRCI", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase 1 OS=Streptomyces cacaoi OX=1898 GN=blaL PE=1 SV=1", result.getSubjectSequenceDescription());
		assertEquals(325, result.getSubjectSequenceLength());
		assertEquals(248, result.getSubjectAlignmentStart());
		assertEquals(257, result.getSubjectAlignmentStop());
		assertEquals(218, result.getSubjectAlignmentDisplayStart());
		assertEquals("FAEDLRAFAVEDGEKAALAPNDREQLNDWMSGSRTGDALIRAGVPKDWKV"
				   + "EDKSGQVKYGTRNDIAVVRPPGRAPIVVSVMSHGDTQDAEPHDELVAEAG"
				   + "LVVADGLK", result.getSubjectAlignmentLine());
		assertEquals("                              ::.: : :::", result.getConsensusLine());

		result = results.get(6);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(39.4d, r = result.getBitScore(), r * delta);
		assertEquals(1.3e-07d, r = result.getExpectationValue(), r * delta);
		assertEquals(97, result.getSmithWatermanScore());
		assertEquals(0.354d, r = result.getIdentity(), r * delta);
		assertEquals(0.729d, r = result.getSimilarity(), r * delta);
		assertEquals(48, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(48, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("--------------------SAVQQKLAALEKSSGGRLGVALIDTADNTQ"
				   + "VLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|P00807|BLAC_STAAU", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase OS=Staphylococcus aureus OX=1280 GN=blaZ PE=1 SV=1", result.getSubjectSequenceDescription());
		assertEquals(281, result.getSubjectSequenceLength());
		assertEquals(21, result.getSubjectAlignmentStart());
		assertEquals(68, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MKKLIFLIVIALVLSACNSNSSHAKELNDLEKKYNAHIGVYALDTKSGKE"
				   + "VKFNSDKRFAYASTSKAINSAILLEQVPYNKLNKKVHINKDDIVAYSPIL"
				   + "EKYVGKDITLKALIEASMTY", result.getSubjectAlignmentLine());
		assertEquals("                    :.  ..:  :::. ....::  .:: .. ."
				   + ": . .:.:: . ::::..", result.getConsensusLine());

		result = results.get(7);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(35.6d, r = result.getBitScore(), r * delta);
		assertEquals(2.1e-06d, r = result.getExpectationValue(), r * delta);
		assertEquals(85, result.getSmithWatermanScore());
		assertEquals(0.340d, r = result.getIdentity(), r * delta);
		assertEquals(0.638d, r = result.getSimilarity(), r * delta);
		assertEquals(47, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(3, result.getQueryAlignmentStart());
		assertEquals(49, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("---------------------SAVQQKLAALEKSSGGRLGVALIDTADNT"
				   + "QVLYRGDERFPMCSTSKVMAA", result.getQueryAlignmentLine());
		assertEquals("sp|Q9K9L8|GLSA1_BACHD", result.getSubjectSequenceName());
		assertEquals("Glutaminase 1 OS=Bacillus halodurans (strain ATCC BAA-125 / DSM 18197 / FERM 7344 / JCM 9153 / C-125) OX=272558 GN=glsA1 PE=3 SV=1", result.getSubjectSequenceDescription());
		assertEquals(308, result.getSubjectSequenceLength());
		assertEquals(24, result.getSubjectAlignmentStart());
		assertEquals(70, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MWKQDETLEQIVLECKKYTEEGTVASYIPALAKADVSTLGIAIYRGGDEQ"
				   + "VIAGDADEKFTLQSISKVIALALALLDVGEEAVFSKVGMEPTGDPFNSIS"
				   + "KLETSVPSKPLNPMINAGAL", result.getSubjectAlignmentLine());
		assertEquals("                       : . . :: :.. . ::.:.   .:. "
				   + " .   .::.: . : :::.:", result.getConsensusLine());

		result = results.get(8);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(34.6d, r = result.getBitScore(), r * delta);
		assertEquals(6.7e-06d, r = result.getExpectationValue(), r * delta);
		assertEquals(82, result.getSmithWatermanScore());
		assertEquals(0.394d, r = result.getIdentity(), r * delta);
		assertEquals(0.758d, r = result.getSimilarity(), r * delta);
		assertEquals(33, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(15, result.getQueryAlignmentStart());
		assertEquals(46, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("---SAVQQKLAALEKSSGGR-LGVALIDTADNTQVLYRGDERFPMCSTSK"
				   + "VMAA", result.getQueryAlignmentLine());
		assertEquals("sp|Q8CDJ3|BAKOR_MOUSE", result.getSubjectSequenceName());
		assertEquals("Beclin 1-associated autophagy-related key regulator OS=Mus musculus OX=10090 GN=Atg14 PE=1 SV=1", result.getSubjectSequenceDescription());
		assertEquals(492, result.getSubjectSequenceLength());
		assertEquals(18, result.getSubjectAlignmentStart());
		assertEquals(50, result.getSubjectAlignmentStop());
		assertEquals(1, result.getSubjectAlignmentDisplayStart());
		assertEquals("MASPSGKGSWTPEAPGFGPRALARDLVDSVDDAEGLYVAVERCPLCNTTR"
				   + "RRLTCAKCVQSGDFVYFDGRDRERFIDKKERLSQLKNKQEEFQKEVLKAM"
				   + "EGKRLTDQLRWKIMSCKMRI", result.getSubjectAlignmentLine());
		assertEquals("                 : :-:.  :.:..:... :: . :: :.:.:..", result.getConsensusLine());
	}

	@Test
	public void testParseResults8() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results8.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(11, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(132.7d, r = result.getBitScore(), r * delta);
		assertEquals(7.6e-35d, r = result.getExpectationValue(), r * delta);
		assertEquals(0, result.getSmithWatermanScore());
		assertEquals(1.000d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(99, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(99, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(99, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("------------------------------CCTGCCGATCTGGTTAACTA"
				   + "CAATCCGATTGCCGAAAAACACGTCAACGGCACAATGACGCTGGCAGAAC"
				   + "TGAGCGCGGCCGCTTTGCAGTACAGCGAC", result.getQueryAlignmentLine());
		assertEquals("ENA|BAA28282|BAA28282.1", result.getSubjectSequenceName());
		assertEquals("Escherichia coli beta-lactamase",
				result.getSubjectSequenceDescription());
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

		// TODO: other 10 results

		assertEquals(Frame.REVERSE, results.get(5).getFrame());
	}

	@Test
	public void testParseResults9() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results9.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(14, results.size());

		result = results.get(4);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(38.1d, r = result.getBitScore(), r * delta);
		assertEquals(7.8e-07d, r = result.getExpectationValue(), r * delta);
		assertEquals(84, result.getSmithWatermanScore());
		assertEquals(0.500d, r = result.getIdentity(), r * delta);
		assertEquals(0.731d, r = result.getSimilarity(), r * delta);
		assertEquals(26, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
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

		// TODO: other results
	}

	@Test
	public void testParseResults10() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results10.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(15, results.size());

		result = results.get(1);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(23.8d, r = result.getBitScore(), r * delta);
		assertEquals(0.014d, r = result.getExpectationValue(), r * delta);
		assertEquals(37, result.getSmithWatermanScore());
		assertEquals(0.667d, r = result.getIdentity(), r * delta);
		assertEquals(1.000d, r = result.getSimilarity(), r * delta);
		assertEquals(6, result.getOverlap());
		assertEquals("query", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(99, result.getQuerySequenceLength());
		assertEquals(80, result.getQueryAlignmentStart());
		assertEquals(94, result.getQueryAlignmentStop());
		assertEquals(80, result.getQueryAlignmentDisplayStart());
		assertEquals("PLCSTA", result.getQueryAlignmentLine());
		assertEquals("sp|O69395|BLT2_ECOLX", result.getSubjectSequenceName());
		assertEquals("Beta-lactamase Toho-2 OS=Escherichia coli OX=562 GN=bla PE=3 SV=1",
				result.getSubjectSequenceDescription());
		assertEquals(289, result.getSubjectSequenceLength());
		assertEquals(70, result.getSubjectAlignmentStart());
		assertEquals(75, result.getSubjectAlignmentStop());
		assertEquals(70, result.getSubjectAlignmentDisplayStart());
		assertEquals("PMCSTS", result.getSubjectAlignmentLine());
		assertEquals(":.:::.", result.getConsensusLine());

		// TODO: other results
	}

	@Test
	public void testParseResults11() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results11.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(31, results.size());

		result = results.get(30);
		assertEquals(Frame.REVERSE, result.getFrame());
		assertEquals(22.0d, r = result.getBitScore(), r * delta);
		assertEquals(0.076d, r = result.getExpectationValue(), r * delta);
		assertEquals(21, result.getSmithWatermanScore());
		assertEquals(0.273d, r = result.getIdentity(), r * delta);
		assertEquals(0.636d, r = result.getSimilarity(), r * delta);
		assertEquals(11, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(39, result.getQueryAlignmentStart());
		assertEquals(49, result.getQueryAlignmentStop());
		assertEquals(39, result.getQueryAlignmentDisplayStart());
		assertEquals("FPMCSTSKVMA", result.getQueryAlignmentLine());
		assertEquals("ENA|CAA25753|CAA25753.1", result.getSubjectSequenceName());
		assertEquals("Bacillus cereus type 1 penicillinase",
				result.getSubjectSequenceDescription());
		assertEquals(921, result.getSubjectSequenceLength());
		assertEquals(647, result.getSubjectAlignmentStart());
		assertEquals(615, result.getSubjectAlignmentStop());
		assertEquals(647, result.getSubjectAlignmentDisplayStart());
		assertEquals("FPTVKALRFVA", result.getSubjectAlignmentLine());
		assertEquals("::  .. . .:", result.getConsensusLine());

		// TODO: other results
	}

	@Test
	public void testParseResults12() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results12.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(22, results.size());

		result = results.get(8);
		assertEquals(Frame.REVERSE, result.getFrame());
		assertEquals(22.8d, r = result.getBitScore(), r * delta);
		assertEquals(0.045d, r = result.getExpectationValue(), r * delta);
		assertEquals(37, result.getSmithWatermanScore());
		assertEquals(0.300d, r = result.getIdentity(), r * delta);
		assertEquals(0.500d, r = result.getSimilarity(), r * delta);
		assertEquals(20, result.getOverlap());
		assertEquals("query1", result.getQuerySequenceName());
		assertEquals("query sequence", result.getQuerySequenceDescription());
		assertEquals(50, result.getQuerySequenceLength());
		assertEquals(27, result.getQueryAlignmentStart());
		assertEquals(46, result.getQueryAlignmentStop());
		assertEquals(27, result.getQueryAlignmentDisplayStart());
		assertEquals("DNTQVLYRGDERFPMCSTSK", result.getQueryAlignmentLine());
		assertEquals("ENA|BAB06346|BAB06346.1", result.getSubjectSequenceName());
		assertEquals("Bacillus halodurans C-125 glutaminase",
				result.getSubjectSequenceDescription());
		assertEquals(927, result.getSubjectSequenceLength());
		assertEquals(345, result.getSubjectAlignmentStart());
		assertEquals(289, result.getSubjectAlignmentStop());
		assertEquals(345, result.getSubjectAlignmentDisplayStart());
		assertEquals("DHRV*RF*GDARFQL*NRIK", result.getSubjectAlignmentLine());
		assertEquals(":.    . :: :: . .  :", result.getConsensusLine());

		// TODO: other results
	}

	@Test
	public void testParseResultsTfastxMgstm1() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results_tfastx_mgstm1_1.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(1, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(Double.NEGATIVE_INFINITY, r = result.getBitScore(), r * delta);
		assertEquals(1.0d, r = result.getExpectationValue(), r * delta);
		assertEquals(12, result.getSmithWatermanScore());
		assertEquals(1.0d, r = result.getIdentity(), r * delta);
		assertEquals(1.0d, r = result.getSimilarity(), r * delta);
		assertEquals(2, result.getOverlap());
		assertEquals("pGT875", result.getQuerySequenceName());
		assertEquals("| 266 with an average of 5% of residues modified by mutr.", result.getQuerySequenceDescription());
		assertEquals(1135, result.getQuerySequenceLength());
		assertEquals(187, result.getQueryAlignmentStart());
		assertEquals(188, result.getQueryAlignmentStop());
		assertEquals(187, result.getQueryAlignmentDisplayStart());
		assertEquals("NT", result.getQueryAlignmentLine());
		assertEquals("sp|P10649|GSTM1_MOUSE", result.getSubjectSequenceName());
		assertEquals("Glutathione S-transferase Mu 1; GST 1-1; GST class-mu 1; Glutathione S-transferase GT8.7; pmGT10",
				result.getSubjectSequenceDescription());
		assertEquals(155, result.getSubjectSequenceLength());
		assertEquals(78, result.getSubjectAlignmentStart());
		assertEquals(83, result.getSubjectAlignmentStop());
		assertEquals(78, result.getSubjectAlignmentDisplayStart());
		assertEquals("NT", result.getSubjectAlignmentLine());
		assertEquals("::", result.getConsensusLine());
	}

	@Test
	public void testParseResultsTfastxMgstm2() throws IOException, FastaResultParserException {
		Reader reader = readResourceFile("results_tfastx_mgstm1_2.txt");
		List<FastaResult> results = new FastaResultParser(reader).parse();
		FastaResult result;
		double r;

		assertEquals(2, results.size());

		result = results.get(0);
		assertEquals(Frame.FORWARD, result.getFrame());
		assertEquals(295.7d, r = result.getBitScore(), r * delta);
		assertEquals(2.3e-84d, r = result.getExpectationValue(), r * delta);
		assertEquals(997, result.getSmithWatermanScore());
		assertEquals(0.872d, r = result.getIdentity(), r * delta);
		assertEquals(0.927d, r = result.getSimilarity(), r * delta);
		assertEquals(219, result.getOverlap());
		assertEquals("sp|P10649|GSTM1_MOUSE", result.getQuerySequenceName());
		assertEquals("Glutathione S-transferase Mu 1; GST 1-1; GST class-mu 1; Glutathione S-transferase GT8.7; pmGT10", result.getQuerySequenceDescription());
		assertEquals(218, result.getQuerySequenceLength());
		assertEquals(1, result.getQueryAlignmentStart());
		assertEquals(218, result.getQueryAlignmentStop());
		assertEquals(1, result.getQueryAlignmentDisplayStart());
		assertEquals("MPMI-LGYWNVRGLTHPIRMLLEYTDSSYDEKRYTMGD-APDFDRSQWLN"
				   + "EK-FKLGLDFP-NLPYLIDGSHKITQ-SNAILRYLA-RKHHLDGETEEER"
				   + "IRADIVENQVM-DTRMQLIMLC-YNPDFEKQKPEFLKTIPEKM-KLYSEF"
				   + "LG-KRPWFAGDK-VTYVDFLAYDILDQYRMFEP-KCLDAFPNLR-DFLAR"
				   + "FEGLKKISA-YMKSSRYIATP-IFSKMAHWSNK", result.getQueryAlignmentLine());
		assertEquals("pGT875", result.getSubjectSequenceName());
		assertEquals("| 266 with an average of 5% of residues modified by mutr.",
				result.getSubjectSequenceDescription());
		assertEquals(1135, result.getSubjectSequenceLength());
		assertEquals(40, result.getSubjectAlignmentStart());
		assertEquals(697, result.getSubjectAlignmentStop());
		assertEquals(40, result.getSubjectAlignmentDisplayStart());
		assertEquals("MPMI/MGYWKVRGLTHPIRMLLEYTDPSYDEKRYTMGD\\APDFDR-QWLN"
				   + "EK\\FKLGLEFP\\NLPYLIDGSHKITQ/ENAILRYLA/HKAHLEEMTEEER"
				   + "IRADIVENQIA\\GNPLQXXMLS\\YNLDFEKQKPEFLKTIPEKM/ELYSEF"
				   + "LGCKRPWFAWDK\\VTYVDFFAYDILDQYRMFEP/KCLDAFPNLR\\DFLAR"
				   + "FEGLKKISA\\YMKSSRYIGTA\\IFTKMAHWSNK", result.getSubjectAlignmentLine());
		assertEquals("::::-.:::.:::::::::::::::: :::::::::::-::::::-::::"
				   + "::-:::::.::-::::::::::::::- ::::::::-.: ::.  :::::"
				   + ":::::::::. - . .:  :: -:: :::::::::::::::::-.:::::"
				   + "::-:::::: ::-::::::.:::::::::::::-::::::::::-:::::"
				   + ":::::::::-::::::::.: -::.::::::::", result.getConsensusLine());
	}

	private Reader readResourceFile(String name) {
		return new InputStreamReader(FastaResultParserTest.class.getResourceAsStream(name));
	}
}