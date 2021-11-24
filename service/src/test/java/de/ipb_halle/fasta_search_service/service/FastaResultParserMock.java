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
package de.ipb_halle.fasta_search_service.service;

import java.util.List;

import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParser;
import de.ipb_halle.fasta_search_service.fastaresult.FastaResultParserException;
import de.ipb_halle.fasta_search_service.models.fastaresult.FastaResult;

/**
 * @author flange
 */
public class FastaResultParserMock extends FastaResultParser {
	private FunctionWithException<String, List<FastaResult>> behaviour;

	@Override
	public List<FastaResult> parse(String input) throws FastaResultParserException {
		return behaviour.apply(input);
	}

	public void setBehaviour(FunctionWithException<String, List<FastaResult>> behaviour) {
		this.behaviour = behaviour;
	}

	@FunctionalInterface
	public interface FunctionWithException<T, R> {
		public R apply(T t) throws FastaResultParserException;
	}
}