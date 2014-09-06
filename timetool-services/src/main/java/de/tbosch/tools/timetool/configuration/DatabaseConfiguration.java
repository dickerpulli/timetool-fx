package de.tbosch.tools.timetool.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.validation.ValidatorFactory;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import de.tbosch.tools.timetool.utils.DatabaseInitializer;

@Configuration
@EnableJpaRepositories(basePackages = { "de.tbosch.tools.timetool.repository" })
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public DatabaseInitializer databaseInitializer() {
		return new DatabaseInitializer();
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("datasource.driverClassName"));
		dataSource.setUrl(env.getProperty("datasource.url"));
		dataSource.setUsername(env.getProperty("datasource.username"));
		dataSource.setPassword(env.getProperty("datasource.password"));
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("de.tbosch.tools.timetool.model");
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		factoryBean.setJpaDialect(jpaDialect());
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(env.getProperty("hibernate.generateDdl", Boolean.class));
		return jpaVendorAdapter;
	}

	@Bean
	public JpaDialect jpaDialect() {
		return new HibernateJpaDialect();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory());
	}

	@Bean
	public ValidatorFactory validatorFactory() {
		return new LocalValidatorFactoryBean();
	}

}
