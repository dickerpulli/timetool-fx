package de.tbosch.tools.timetool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tbosch.tools.timetool.model.Configuration;
import de.tbosch.tools.timetool.model.Configuration.Key;

public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

	public Configuration findByKey(Key key);

}
