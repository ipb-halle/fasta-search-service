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
package de.ipb_halle.fasta_search_service.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.inject.Inject;

import org.apache.openejb.jee.EnvEntry;
import org.apache.openejb.jee.WebApp;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Classes;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author flange
 */
@RunWith(ApplicationComposer.class)
public class DatabaseConnectionConfigurationServiceTest {
	@Inject
	private DatabaseConnectionConfigurationService service;

	@Module
	@Classes(cdi = true, value = { DatabaseConnectionConfigurationService.class })
	public WebApp app() {
		WebApp app = new WebApp();
		Collection<EnvEntry> env = app.getEnvEntry();
		env.add(new EnvEntry("dbHostname", "java.lang.String", "hostname"));
		env.add(new EnvEntry("dbPort", "java.lang.Integer", "1234"));
		env.add(new EnvEntry("dbName", "java.lang.String", "database"));
		env.add(new EnvEntry("dbUser", "java.lang.String", "user"));
		env.add(new EnvEntry("dbPassword", "java.lang.String", "password"));
		return app;
	}

	@Test
	public void test_serviceInjectsResources() {
		assertTrue(service.hasConfig());
		assertEquals("hostname:1234 database user password", service.getConnectionString());
	}

	@Test
	public void test_validations() {
		DatabaseConnectionConfigurationService service = new DatabaseConnectionConfigurationService();

		service.init();
		assertHasNoConfig(service);

		service.setDbHostname("");
		service.init();
		assertHasNoConfig(service);

		service.setDbHostname("abc");
		service.init();
		assertHasNoConfig(service);

		service.setDbPort(123);
		service.init();
		assertHasNoConfig(service);

		service.setDbName("");
		service.init();
		assertHasNoConfig(service);

		service.setDbName("def");
		service.init();
		assertHasNoConfig(service);

		service.setDbUser("");
		service.init();
		assertHasNoConfig(service);

		service.setDbUser("ghi");
		service.init();
		assertHasNoConfig(service);

		service.setDbPassword("");
		service.init();
		assertTrue(service.hasConfig());
		assertEquals("abc:123 def ghi ", service.getConnectionString());

		service.setDbPassword("jkl");
		service.init();
		assertTrue(service.hasConfig());
		assertEquals("abc:123 def ghi jkl", service.getConnectionString());
	}

	private void assertHasNoConfig(DatabaseConnectionConfigurationService service) {
		assertFalse(service.hasConfig());
		assertNull(service.getConnectionString());
	}
}