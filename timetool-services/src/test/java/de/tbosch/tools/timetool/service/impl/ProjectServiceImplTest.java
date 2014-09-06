package de.tbosch.tools.timetool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringDbJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.service.ProjectService;

@ContextConfiguration(classes = TestConfiguration.class)
public class ProjectServiceImplTest extends AbstractSpringDbJavaConfigTest {

	@Autowired
	private ProjectService projectService;

	@Before
	public void before() throws Exception {
		executeSql("database/delete-tables.sql");
		executeSql("database/service/ProjectServiceImplTest.sql");
	}

	@Test(expected = ConstraintViolationException.class)
	public void testNewProject() {
		projectService.createProject(null, 1L);
	}

	@Test
	public void testSetActiveProject() {
		Project project = projectService.getActiveProject();
		assertEquals(1L, project.getId());
		projectService.setActiveProject(2L);
		project = projectService.getActiveProject();
		assertEquals(2L, project.getId());
	}

	@Test
	public void testGetProject() {
		Project project = projectService.getProject(1L);
		assertNotNull(project);
	}

	@Test
	public void testGetActiveProject() {
		Project project = projectService.getActiveProject();
		assertEquals(1L, project.getId());
	}

	@Test
	public void testDeleteProject() {
		Project project = projectService.getProject(3L);
		assertNotNull(project);
		projectService.deleteProject(project);
		project = projectService.getProject(3L);
		assertNull(project);
	}

}
