package de.tbosch.tools.timetool.utils.context;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import de.tbosch.tools.timetool.AbstractSpringTest;

public class TimetoolContextTest extends AbstractSpringTest {

	@Test
	public void getBeansByType() {
		List<String> list = TimetoolContext.getBeansByType(String.class);
		assertEquals(2, list.size());
		assertEquals("test1", list.get(0));
		assertEquals("test2", list.get(1));
	}

}
