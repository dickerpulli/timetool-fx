package de.tbosch.tools.timetool.repository;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;
import de.tbosch.tools.timetool.model.Customer;
import de.tbosch.tools.timetool.model.Project;

@ContextConfiguration(classes = TestConfiguration.class)
public class ProjectRepositoryTest extends AbstractSpringJavaConfigTest {

	@Autowired
	private ProjectRepository repository;

	@Test(expected = ConstraintViolationException.class)
	public void testNewProjectFail() {
		repository.save(new Project());
	}

	@Test
	public void testNewProjectSuccess() {
		Project project = new Project();
		project.setName("p1");
		Customer customer = new Customer();
		customer.setName("c1");
		project.setCustomer(customer);
		repository.save(project);
	}

}
