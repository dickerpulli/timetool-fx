package de.tbosch.tools.timetool;

import java.io.IOException;

import javax.sql.DataSource;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback = true)
public abstract class AbstractSpringDbTest extends AbstractSpringTest {

	@Autowired
	private DataSource dataSource;

	private SimpleJdbcTemplate simpleJdbcTemplate;

	@Before
	public void beforeAbstractDb() {
		simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	public void executeSql(String filename) throws IOException {
		SimpleJdbcTestUtils.executeSqlScript(simpleJdbcTemplate, new ClassPathResource(filename), false);
	}

}
