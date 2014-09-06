package de.tbosch.tools.timetool.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({ ServicesConfiguration.class, DatabaseConfiguration.class })
@ImportResource("applicationContext-jira.xml")
@PropertySources({ @PropertySource("timetool-services.properties"), @PropertySource("timetool-services-test.properties") })
public class TestConfiguration {

	@Bean
	public String testGetBeansByType1() {
		return "test1";
	}

	@Bean
	public String testGetBeansByType2() {
		return "test2";
	}

}
