package de.tbosch.tools.timetool.configuration;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import de.tbosch.tools.timetool.TimetoolThread;
import de.tbosch.tools.timetool.gui.JXTrayIcon;
import de.tbosch.tools.timetool.utils.timer.IconTimerTask;

@Configuration
@Import({ ServicesConfiguration.class, DatabaseConfiguration.class, JiraConfiguration.class })
@PropertySource("timetool.properties")
@ComponentScan("de.tbosch.tools.timetool.controller")
@EnableScheduling
public class ApplicationConfiguration implements SchedulingConfigurer {

	@Autowired
	private Environment env;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addFixedRateTask(iconTimerTask(), env.getProperty("iconTimer.period", Long.class));
	}

	@Bean
	public IconTimerTask iconTimerTask() {
		return new IconTimerTask();
	}

	@Bean
	public MessageSource resourceBundleMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages", "version");
		return messageSource;
	}

	@Bean
	public TimetoolThread thread() {
		return new TimetoolThread();
	}

	@Bean
	public Image imageAktiv() throws IOException {
		return new ImageIcon(new ClassPathResource(env.getProperty("image.aktiv")).getURL()).getImage();
	}

	@Bean
	public Image imageInaktiv() throws IOException {
		return new ImageIcon(new ClassPathResource(env.getProperty("image.inaktiv")).getURL()).getImage();

	}

	@Bean
	public JXTrayIcon trayIcon() throws IOException {
		return new JXTrayIcon(imageInaktiv());
	}

}
