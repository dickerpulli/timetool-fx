package de.tbosch.tools.timetool;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractSpringDbTest extends AbstractSpringTest {

	@Autowired
	private DataSource dataSource;

	public void executeSql(String filename) throws Exception {
		Connection connection = DataSourceUtils.getConnection(dataSource);
		ScriptUtils.executeSqlScript(connection, new ClassPathResource(filename));
	}

}
