package de.tbosch.tools.timetool.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.dao.ProjectDao;
import de.tbosch.tools.timetool.model.Project;

public class ProjectDaoImplTest extends AbstractSpringDbTest {

	@Autowired
	private ProjectDao projectDao;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/ProjectDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Project project = projectDao.read(1L);
		assertNotNull(project);
	}

}
