package de.tbosch.tools.timetool.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import de.tbosch.tools.timetool.service.ConfigurationService;
import de.tbosch.tools.timetool.service.CustomerService;
import de.tbosch.tools.timetool.service.ProjectService;
import de.tbosch.tools.timetool.service.TimeslotService;
import de.tbosch.tools.timetool.service.impl.ConfigurationServiceImpl;
import de.tbosch.tools.timetool.service.impl.CustomerServiceImpl;
import de.tbosch.tools.timetool.service.impl.ProjectServiceImpl;
import de.tbosch.tools.timetool.service.impl.TimeslotServiceImpl;
import de.tbosch.tools.timetool.utils.timer.TimeslotTimerTask;

@Configuration
@PropertySource("timetool-services.properties")
@EnableScheduling
public class ServicesConfiguration implements SchedulingConfigurer {

	@Autowired
	private Environment env;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addFixedRateTask(timeslotTimerTask(), env.getProperty("timeslotTimer.period", Long.class));
	}

	@Bean
	public TimeslotTimerTask timeslotTimerTask() {
		return new TimeslotTimerTask();
	}

	@Bean
	public ConfigurationService configurationService() {
		return new ConfigurationServiceImpl();
	}

	@Bean
	public CustomerService customerService() {
		return new CustomerServiceImpl();
	}

	@Bean
	public ProjectService projectService() {
		return new ProjectServiceImpl();
	}

	@Bean
	public TimeslotService timeslotService() {
		return new TimeslotServiceImpl();
	}

}
