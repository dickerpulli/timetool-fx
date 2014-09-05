package de.tbosch.tools.timetool;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.tbosch.tools.timetool.utils.context.TimetoolContext;

@ContextConfiguration(locations = "/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSpringTest {

	@Autowired
	private ConfigurableApplicationContext context;

	@Before
	public void beforeAbstractSpring() {
		TimetoolContext.load(context);
	}

}
