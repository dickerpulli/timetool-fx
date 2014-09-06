package de.tbosch.tools.timetool;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tbosch.tools.timetool.utils.context.TimetoolContext;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSpringJavaConfigTest {

	@Autowired
	private ConfigurableApplicationContext context;

	@Before
	public void beforeAbstractSpring() {
		TimetoolContext.load(context);
	}

}
