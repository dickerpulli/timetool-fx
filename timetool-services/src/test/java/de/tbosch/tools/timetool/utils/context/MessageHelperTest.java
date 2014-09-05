package de.tbosch.tools.timetool.utils.context;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessageHelperTest {

	@Test
	public void testGetMessage() {
		String message = MessageHelper.getMessage("button.ok");
		assertEquals("Ok", message);
	}

}
