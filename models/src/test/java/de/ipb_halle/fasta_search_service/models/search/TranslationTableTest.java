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
package de.ipb_halle.fasta_search_service.models.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author flange
 */
public class TranslationTableTest {
	@Test
	public void test_numberOfItems() {
		assertThat("Please check the tests whenever you change the enum TranslationTable!",
				TranslationTable.values().length, is(17));
	}

	@Test
	public void test_fromId() {
		assertEquals(TranslationTable.STANDARD, TranslationTable.fromId(1));
		assertEquals(TranslationTable.VERTEBRATE_MITOCHONDRIAL, TranslationTable.fromId(2));
		assertEquals(TranslationTable.YEAST_MITOCHONDRIAL, TranslationTable.fromId(3));
		assertEquals(TranslationTable.MOLD_MITOCHONDRIAL_PROTOZOAN_MITOCHONDRIAL_COELENTERATE, TranslationTable.fromId(4));
		assertEquals(TranslationTable.INVERTEBRATE_MITOCHONDRIAL, TranslationTable.fromId(5));
		assertEquals(TranslationTable.CILIATE_NUCLEAR_DASYCLADACEAN_NUCLEAR_HEXAMITA_NUCLEAR, TranslationTable.fromId(6));
		assertEquals(TranslationTable.ECHINODERM_MITOCHONDRIAL_FLATWORM_MITOCHONDRIAL, TranslationTable.fromId(9));
		assertEquals(TranslationTable.EUPLOTID_NUCLEAR, TranslationTable.fromId(10));
		assertEquals(TranslationTable.BACTERIAL_AND_PLANT_PLASTID, TranslationTable.fromId(11));
		assertEquals(TranslationTable.ALTERNATIVE_YEAST_NUCLEAR, TranslationTable.fromId(12));
		assertEquals(TranslationTable.ASCIDIAN_MITOCHONDRIAL, TranslationTable.fromId(13));
		assertEquals(TranslationTable.ALTERNATIVE_FLATWORM_MITOCHONDRIAL, TranslationTable.fromId(14));
		assertEquals(TranslationTable.BLEPHARISMA_MACRONUCLEAR, TranslationTable.fromId(15));
		assertEquals(TranslationTable.CHLOROPHYCEAN_MITOCHONDRIAL, TranslationTable.fromId(16));
		assertEquals(TranslationTable.TREMATODE_MITOCHONDRIAL, TranslationTable.fromId(21));
		assertEquals(TranslationTable.SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL, TranslationTable.fromId(22));
		assertEquals(TranslationTable.THRAUSTOCHYTRIUM_MITOCHONDRIAL, TranslationTable.fromId(23));

		assertNull(TranslationTable.fromId(-100));
	}
}