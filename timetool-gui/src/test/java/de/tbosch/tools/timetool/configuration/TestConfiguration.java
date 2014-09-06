package de.tbosch.tools.timetool.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import(ApplicationConfiguration.class)
@PropertySources({ @PropertySource("timetool.properties"), @PropertySource("timetool-services.properties"), @PropertySource("timetool-test.properties") })
public class TestConfiguration {

}
