package de.tbosch.tools.timetool.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.tbosch.tools.timetool.AbstractSpringDbTest;
import de.tbosch.tools.timetool.dao.TimeslotDao;
import de.tbosch.tools.timetool.model.Project;
import de.tbosch.tools.timetool.model.Timeslot;

public class TimeslotDaoImplTest extends AbstractSpringDbTest {

	@Autowired
	private TimeslotDao timeslotDao;

	@Before
	public void before() throws IOException {
		executeSql("database/delete-tables.sql");
		executeSql("database/dao/TimeslotDaoImplTest.sql");
	}

	@Test
	public void testRead() {
		Timeslot timeslot = timeslotDao.read(1L);
		assertNotNull(timeslot);
	}

	@Test
	public void testCreate() throws ParseException {
		Timeslot timeslot = new Timeslot();
		timeslot.setStarttime(new Date());
		timeslot.setEndtime(new Date());
		Project project = new Project();
		project.setId(1L);
		timeslot.setProject(project);
		timeslotDao.create(timeslot);
		List<Timeslot> all = timeslotDao.findAll();
		assertEquals(4, all.size());
	}

	@Test
	public void testFindActive() {
		Timeslot timeslot = timeslotDao.findActive();
		assertNotNull(timeslot);
	}

	@Test
	public void testFindActive2() {
		Timeslot timeslot = timeslotDao.findActive();
		assertNotNull(timeslot);
	}

	@Test
	public void findWithTodayStarttime() throws InterruptedException {
		Project project = new Project();
		project.setId(2L);
		Timeslot timeslot1 = new Timeslot();
		timeslot1.setStarttime(new Date());
		timeslot1.setEndtime(new Date());
		timeslot1.setProject(project);
		Thread.sleep(100);
		Timeslot timeslot2 = new Timeslot();
		timeslot2.setStarttime(new Date());
		timeslot2.setEndtime(new Date());
		timeslot2.setProject(project);
		Timeslot timeslot3 = new Timeslot();
		timeslot3.setStarttime(new DateTime(2010, 1, 1, 12, 0, 0, 0).toDate());
		timeslot3.setEndtime(new Date());
		timeslot3.setProject(project);
		timeslotDao.create(timeslot1);
		long id = timeslotDao.create(timeslot2);
		timeslotDao.create(timeslot3);
		Timeslot timeslot = timeslotDao.findWithTodayStarttime(2L);
		assertNotNull(timeslot);
		assertEquals(id, timeslot.getId());
	}

}
