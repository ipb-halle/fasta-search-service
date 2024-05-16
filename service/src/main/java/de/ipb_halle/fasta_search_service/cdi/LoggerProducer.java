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
package de.ipb_halle.fasta_search_service.cdi;

import java.util.logging.Logger;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

/**
 * CDI-Producer of logger objects.
 * 
 * @author flange
 */
/*
 * Code from https://stackoverflow.com/a/21092326
 */
@Dependent
public class LoggerProducer {
	/**
	 * Produces a logger object.
	 * 
	 * @param p point where the logger object is to be injected
	 * @return logger object
	 */
	@Produces
	public Logger getLogger(InjectionPoint p) {
		return Logger.getLogger(p.getClass().getCanonicalName());
	}
}
