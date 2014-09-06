package de.tbosch.tools.timetool.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tbosch.tools.timetool.service.JiraService;
import de.tbosch.tools.timetool.service.impl.JiraServiceImpl;

@Configuration
public class JiraConfiguration {

	@Bean
	public JiraService jiraService() {
		return new JiraServiceImpl();
	}

}
