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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;

/**
 * EJB service that provides a database connection string from the environment
 * entries of the context container. See the
 * <a href="https://tomcat.apache.org/tomcat-9.0-doc/config/context.html">Apache
 * Tomcat Configuration Reference</a> about how to configure a context
 * container.
 * <p>
 * Example for database connection configuration in this project using Apache TomEE:<p>
 * fasta-search-service.xml in tomee_home/conf/Catalina/localhost/:
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Context>
 * 	<Environment name="dbHostname" value="hostname" type="java.lang.String" override="true" />
 * 	<Environment name="dbPort" value="1234" type="java.lang.Integer" override="true" />
 * 	<Environment name="dbName" value="database" type="java.lang.String" override="true" />
 * 	<Environment name="dbUser" value="user" type="java.lang.String" override="true" />
 * 	<Environment name="dbPassword" value="password" type="java.lang.String" override="true" />
 * </Context>
 * }
 * </pre>
 * 
 * @author flange
 */
@Singleton
public class DatabaseConnectionConfigurationService {
	@Resource
	private String dbHostname;

	@Resource
	private Integer dbPort;

	@Resource
	private String dbName;

	@Resource
	private String dbUser;

	@Resource
	private String dbPassword;

	private static final String connectionStringFormat = "%s:%d %s %s %s";
	private String connectionString = null;

	@PostConstruct
	public void init() {
		if (hasAllFields()) {
			connectionString = String.format(connectionStringFormat, dbHostname, dbPort.intValue(), dbName, dbUser,
					dbPassword);
		}
	}

	private boolean hasAllFields() {
		return (isNotNullAndNotEmpty(dbHostname) && (dbPort != null) && isNotNullAndNotEmpty(dbName)
				&& isNotNullAndNotEmpty(dbUser) && (dbPassword != null));
	}

	private boolean isNotNullAndNotEmpty(String s) {
		return (s != null) && !s.isEmpty();
	}

	/**
	 * @return database connection string to used in a library file for fasta
	 *         searches
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @return {@code true} in case there is a valid configuration
	 */
	public boolean hasConfig() {
		return connectionString != null;
	}

	public String getDbHostname() {
		return dbHostname;
	}

	public void setDbHostname(String dbHostname) {
		this.dbHostname = dbHostname;
	}

	public Integer getDbPort() {
		return dbPort;
	}

	public void setDbPort(Integer dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
}