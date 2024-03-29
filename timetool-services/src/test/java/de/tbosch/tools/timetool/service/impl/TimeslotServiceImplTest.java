package de.tbosch.tools.timetool.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringDbJavaConfigTest;
import de.tbosch.tools.timetool.configuration.TestConfiguration;
import de.tbosch.tools.timetool.exception.ServiceBusinessException;
import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.service.TimeslotService;

@ContextConfiguration(classes = TestConfiguration.class)
public class TimeslotServiceImplTest extends AbstractSpringDbJavaConfigTest {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	@Autowired
	private TimeslotService timeslotService;

	@Before
	public void before() throws Exception {
		executeSql("database/delete-tables.sql");
		executeSql("database/service/TimeslotServiceImplTest.sql");
	}

	@Test
	public void testStartTimeslot() {
		assertEquals(3, timeslotService.getAllTimeslots().size());
		timeslotService.deactivateTimeslot(3L);
		timeslotService.activateTimeslot(1L);
		assertEquals(4, timeslotService.getAllTimeslots().size());
		Timeslot timeslot = timeslotService.getActiveTimeslot();
		assertNotNull(timeslot);
	}

	@Test
	public void testGetAllTimeslotsPerProject() {
		Map<String, List<Timeslot>> timeslotsPerProject = timeslotService.getAllTimeslotsPerProject();
		assertEquals(1, timeslotsPerProject.size());
		assertEquals("customer1 - project1", timeslotsPerProject.keySet().iterator().next());
		assertEquals(1, timeslotsPerProject.get("customer1 - project1").get(0).getId());
	}

	@Test
	public void testGetActiveTimeslot() {
		Timeslot timeslot = timeslotService.getActiveTimeslot();
		assertNotNull(timeslot);
	}

	@Test
	public void testEndTimeslot() {
		timeslotService.deactivateTimeslot(3L);
		Timeslot timeslot = timeslotService.getTimeslot(3L);
		assertEquals(false, timeslot.isActive());
	}

	@Test
	public void testSumTime() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));
		timeslot1.setEndtime(sdf.parse("01.01.2009 07:59:00"));

		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("02.01.2009 00:00:00"));
		timeslot2.setEndtime(sdf.parse("02.01.2009 07:59:00"));

		String time = timeslotService.addTimeslots(Arrays.asList(timeslot1, timeslot2));
		assertEquals("15h 58m", time);
	}

	@Test
	public void validateForSending() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("02.01.2009 00:00:00"));

		Timeslot timeslot3 = new Timeslot();
		timeslot3.setStarttime(sdf.parse("02.01.2009 00:00:00"));

		try {
			timeslotService.validateForSending(Arrays.asList(timeslot1, timeslot2, timeslot3));
			fail("Exception expected");
		} catch (ServiceBusinessException e) {
			// nix
		}

		// Next

		timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("02.01.2009 00:00:00"));

		timeslot3 = new Timeslot();
		timeslot3.setStarttime(sdf.parse("03.01.2009 00:00:00"));

		try {
			timeslotService.validateForSending(Arrays.asList(timeslot1, timeslot2, timeslot3));
			fail("Exception expected");
		} catch (ServiceBusinessException e) {
			// nix
		}

		// Next

		timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot3 = new Timeslot();
		timeslot3.setStarttime(sdf.parse("02.01.2009 00:00:00"));

		try {
			timeslotService.validateForSending(Arrays.asList(timeslot1, timeslot2, timeslot3));
			fail("Exception expected");
		} catch (ServiceBusinessException e) {
			// nix
		}

		// Next

		timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot3 = new Timeslot();
		timeslot3.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		try {
			timeslotService.validateForSending(Arrays.asList(timeslot1, timeslot2, timeslot3));
		} catch (ServiceBusinessException e) {
			fail("No Exception expected");
		}

		// Next

		timeslot1 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot2 = new Timeslot();
		timeslot2.setStarttime(sdf.parse("01.01.2009 00:00:00"));

		timeslot3 = new Timeslot();
		timeslot3.setStarttime(sdf.parse("01.01.2009 12:00:00"));

		try {
			timeslotService.validateForSending(Arrays.asList(timeslot1, timeslot2, timeslot3));
		} catch (ServiceBusinessException e) {
			fail("No Exception expected");
		}
	}

}
