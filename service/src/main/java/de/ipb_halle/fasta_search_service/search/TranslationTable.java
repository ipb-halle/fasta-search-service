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
package de.ipb_halle.fasta_search_service.search;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Genetic code tables available in the fasta36 program suite. 
 * 
 * @author flange
 */
public enum TranslationTable {
	/*
	 * See https://github.com/wrpearson/fasta36/blob/v36.3.8/src/faatran.c for the
	 * list of available translation tables in fasta36. The names were taken from
	 * https://www.ncbi.nlm.nih.gov/Taxonomy/Utils/wprintgc.cgi and
	 * https://www.ebi.ac.uk/seqdb/confluence/display/JDSAT/FASTA+Help+and+Documentation
	 */
	STANDARD(1),
	VERTEBRATE_MITOCHONDRIAL(2),
	YEAST_MITOCHONDRIAL(3),
	MOLD_MITOCHONDRIAL_PROTOZOAN_MITOCHONDRIAL_COELENTERATE(4),
	INVERTEBRATE_MITOCHONDRIAL(5),
	CILIATE_NUCLEAR_DASYCLADACEAN_NUCLEAR_HEXAMITA_NUCLEAR(6),
	ECHINODERM_MITOCHONDRIAL_FLATWORM_MITOCHONDRIAL(9),
	EUPLOTID_NUCLEAR(10),
	BACTERIAL_AND_PLANT_PLASTID(11),
	ALTERNATIVE_YEAST_NUCLEAR(12),
	ASCIDIAN_MITOCHONDRIAL(13),
	ALTERNATIVE_FLATWORM_MITOCHONDRIAL(14),
	BLEPHARISMA_MACRONUCLEAR(15),
	CHLOROPHYCEAN_MITOCHONDRIAL(16),
	TREMATODE_MITOCHONDRIAL(21),
	SCENEDESMUS_OBLIQUUS_MITOCHONDRIAL(22),
	THRAUSTOCHYTRIUM_MITOCHONDRIAL(23);

	private static final Map<Integer, TranslationTable> idToItemMap = Stream.of(values())
			.collect(toMap(e -> e.getId(), e -> e));
	private final int id;

	private TranslationTable(int id) {
		this.id = id;
	}

	/**
	 * @return id number of the {@link TranslationTable} item
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return {@link TranslationTable} item from the given translation table id
	 *         number or {@code null} if there is no item with that id
	 */
	public static TranslationTable fromId(int id) {
		return idToItemMap.get(id);
	}
}