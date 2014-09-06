package de.tbosch.tools.timetool.service.impl;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import de.tbosch.tools.timetool.AbstractSpringJavaConfigTest;
import de.tbosch.tools.timetool.configuration.JiraConfiguration;
import de.tbosch.tools.timetool.exception.JiraBusinessException;
import de.tbosch.tools.timetool.model.JiraSettings;
import de.tbosch.tools.timetool.service.JiraService;

@ContextConfiguration(classes = JiraConfiguration.class)
@Ignore
public class JiraServiceImplTest extends AbstractSpringJavaConfigTest {

	@Autowired
	private JiraService jiraService;

	@Before
	public void before() throws IOException {
	}

	@Test(expected = JiraBusinessException.class)
	public void addWorklogFail() {
		String name = "TIMXIII-269";
		DateTime startDate = new LocalDateTime(2014, 9, 5, 8, 1).toDateTime();
		int minutesSpent = 1;
		String comment = "test";
		String url = "https://localhost/1234";
		String user = "";
		String pass = "";
		JiraSettings settings = new JiraSettings(url, user, pass);
		jiraService.addWorklog(name, startDate, minutesSpent, comment, settings);
	}

	@Test
	public void addWorklog() {
		String name = "TIMXIII-269";
		DateTime startDate = new LocalDateTime(2014, 9, 5, 8, 1).toDateTime();
		int minutesSpent = 1;
		String comment = "test";
		String url = "https://jira.codecentric.de";
		String user = "";
		String pass = "";
		JiraSettings settings = new JiraSettings(url, user, pass);
		jiraService.addWorklog(name, startDate, minutesSpent, comment, settings);
	}

	@Test
	public void getSummary() {
		String name = "TIMXIII-269";
		String url = "https://jira.codecentric.de";
		String user = "";
		String pass = "";
		JiraSettings settings = new JiraSettings(url, user, pass);
		System.out.println(jiraService.getSummary(name, settings));
	}

	@Test
	public void getTicketNamesBySearchText() {
		String name = "Mule";
		String url = "https://jira.codecentric.de";
		String user = "";
		String pass = "";
		JiraSettings settings = new JiraSettings(url, user, pass);
		System.out.println(jiraService.getTicketNamesBySearchText(name, settings));
	}

}
