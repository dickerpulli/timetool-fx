package de.tbosch.tools.timetool.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

	@Autowired
	private DataSource dataSource;

	/**
	 * Configure HSQL-DB specific properties.
	 */
	public void configureDatabase() {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		ScriptUtils.executeSqlScript(connection, new ClassPathResource("hsqldb-init.sql"));
	}

}
