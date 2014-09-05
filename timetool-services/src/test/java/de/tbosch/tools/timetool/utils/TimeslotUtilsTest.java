package de.tbosch.tools.timetool.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import de.tbosch.tools.timetool.model.Timeslot;
import de.tbosch.tools.timetool.utils.TimeslotUtils.Workday;

public class TimeslotUtilsTest {

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	@Test
	public void testGetTimeslotsPerMonth() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		Timeslot timeslot2 = new Timeslot();
		Timeslot timeslot3 = new Timeslot();
		Timeslot timeslot4 = new Timeslot();
		Timeslot timeslot5 = new Timeslot();
		Timeslot timeslot6 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 12:00"));
		timeslot2.setStarttime(sdf.parse("02.01.2009 12:00"));
		timeslot3.setStarttime(sdf.parse("01.02.2009 12:00"));
		timeslot4.setStarttime(sdf.parse("01.03.2009 12:00"));
		timeslot5.setStarttime(sdf.parse("01.04.2009 00:00"));
		timeslot6.setStarttime(sdf.parse("30.04.2009 23:59"));
		List<Timeslot> all = new ArrayList<Timeslot>();
		all.add(timeslot1);
		all.add(timeslot2);
		all.add(timeslot3);
		all.add(timeslot4);
		all.add(timeslot5);
		all.add(timeslot6);

		Map<Integer, List<Timeslot>> timeslots = TimeslotUtils.getTimeslotsPerMonth(all);

		assertNotNull(timeslots);
		assertEquals(4, timeslots.size());
		assertEquals(2, timeslots.get(1).size());
		assertEquals(1, timeslots.get(2).size());
		assertEquals(1, timeslots.get(3).size());
		assertEquals(2, timeslots.get(4).size());
		assertTrue(timeslots.get(1).contains(timeslot1));
		assertTrue(timeslots.get(1).contains(timeslot2));
		assertTrue(timeslots.get(2).contains(timeslot3));
		assertTrue(timeslots.get(3).contains(timeslot4));
		assertTrue(timeslots.get(4).contains(timeslot5));
		assertTrue(timeslots.get(4).contains(timeslot6));
	}

	@Test
	public void getTimeslotsPerMonthAndDay() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		Timeslot timeslot2 = new Timeslot();
		Timeslot timeslot3 = new Timeslot();
		Timeslot timeslot4 = new Timeslot();
		Timeslot timeslot5 = new Timeslot();
		Timeslot timeslot6 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 12:00"));
		timeslot2.setStarttime(sdf.parse("02.01.2009 12:00"));
		timeslot3.setStarttime(sdf.parse("01.02.2009 12:00"));
		timeslot4.setStarttime(sdf.parse("01.02.2009 13:00"));
		timeslot5.setStarttime(sdf.parse("01.04.2009 00:00"));
		timeslot6.setStarttime(sdf.parse("01.04.2009 23:59"));
		List<Timeslot> all = new ArrayList<Timeslot>();
		all.add(timeslot1);
		all.add(timeslot2);
		all.add(timeslot3);
		all.add(timeslot4);
		all.add(timeslot5);
		all.add(timeslot6);

		Map<LocalDate, Map<LocalDate, List<Timeslot>>> perMonthAndDay = TimeslotUtils.getTimeslotsPerMonthAndDay(all);

		assertEquals(timeslot1, perMonthAndDay.get(new LocalDate(2009, 1, 1)).get(new LocalDate(2009, 1, 1)).get(0));
		assertEquals(timeslot2, perMonthAndDay.get(new LocalDate(2009, 1, 1)).get(new LocalDate(2009, 1, 2)).get(0));
		assertEquals(timeslot3, perMonthAndDay.get(new LocalDate(2009, 2, 1)).get(new LocalDate(2009, 2, 1)).get(0));
		assertEquals(timeslot4, perMonthAndDay.get(new LocalDate(2009, 2, 1)).get(new LocalDate(2009, 2, 1)).get(1));
		assertEquals(timeslot5, perMonthAndDay.get(new LocalDate(2009, 4, 1)).get(new LocalDate(2009, 4, 1)).get(0));
		assertEquals(timeslot6, perMonthAndDay.get(new LocalDate(2009, 4, 1)).get(new LocalDate(2009, 4, 1)).get(1));
	}

	@Test
	public void testToInterval() throws ParseException {
		Timeslot timeslot = new Timeslot();
		timeslot.setStarttime(sdf.parse("01.01.2009 12:00"));
		timeslot.setEndtime(sdf.parse("01.01.2009 13:00"));
		Interval interval = TimeslotUtils.toInterval(timeslot);
		assertEquals("01-01-2009 12:00:00", interval.getStart().toString("dd-MM-yyyy HH:mm:ss"));
		assertEquals("01-01-2009 13:00:00", interval.getEnd().toString("dd-MM-yyyy HH:mm:ss"));
	}

	@Test
	public void testGetWorkdays() throws ParseException {
		Timeslot timeslot1 = new Timeslot();
		Timeslot timeslot2 = new Timeslot();
		Timeslot timeslot3 = new Timeslot();
		Timeslot timeslot4 = new Timeslot();
		Timeslot timeslot5 = new Timeslot();
		Timeslot timeslot6 = new Timeslot();
		timeslot1.setStarttime(sdf.parse("01.01.2009 12:00"));
		timeslot1.setEndtime(sdf.parse("01.01.2009 13:00"));
		timeslot2.setStarttime(sdf.parse("02.01.2009 12:00"));
		timeslot2.setEndtime(sdf.parse("02.01.2009 14:00"));
		timeslot3.setStarttime(sdf.parse("01.02.2009 12:00"));
		timeslot3.setEndtime(sdf.parse("01.02.2009 13:00"));
		timeslot4.setStarttime(sdf.parse("01.02.2009 14:00"));
		timeslot4.setEndtime(sdf.parse("01.02.2009 15:00"));
		timeslot5.setStarttime(sdf.parse("01.04.2009 00:00"));
		timeslot5.setEndtime(sdf.parse("01.04.2009 01:00"));
		timeslot6.setStarttime(sdf.parse("01.04.2009 01:58"));
		timeslot6.setEndtime(sdf.parse("01.04.2009 01:59"));
		List<Timeslot> all = new ArrayList<Timeslot>();
		all.add(timeslot1);
		all.add(timeslot2);
		all.add(timeslot3);
		all.add(timeslot4);
		all.add(timeslot5);
		all.add(timeslot6);
		List<Workday> workdays = TimeslotUtils.getWorkdays(all);
		assertEquals(4, workdays.size());
		assertEquals("01.01.2009 12:00", workdays.get(0).getInterval().getStart().toString("dd.MM.yyyy HH:mm"));
		assertEquals("01.01.2009 13:00", workdays.get(0).getInterval().getEnd().toString("dd.MM.yyyy HH:mm"));
		assertEquals("PT0S", workdays.get(0).getPause().toString());
		assertEquals("02.01.2009 12:00", workdays.get(1).getInterval().getStart().toString("dd.MM.yyyy HH:mm"));
		assertEquals("02.01.2009 14:00", workdays.get(1).getInterval().getEnd().toString("dd.MM.yyyy HH:mm"));
		assertEquals("PT0S", workdays.get(1).getPause().toString());
		assertEquals("01.02.2009 12:00", workdays.get(2).getInterval().getStart().toString("dd.MM.yyyy HH:mm"));
		assertEquals("01.02.2009 15:00", workdays.get(2).getInterval().getEnd().toString("dd.MM.yyyy HH:mm"));
		assertEquals("PT3600S", workdays.get(2).getPause().toString());
		assertEquals("01.04.2009 00:00", workdays.get(3).getInterval().getStart().toString("dd.MM.yyyy HH:mm"));
		assertEquals("01.04.2009 01:59", workdays.get(3).getInterval().getEnd().toString("dd.MM.yyyy HH:mm"));
		assertEquals("PT3480S", workdays.get(3).getPause().toString());
	}
}
