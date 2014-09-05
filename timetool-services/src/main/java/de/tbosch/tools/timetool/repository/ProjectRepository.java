package de.tbosch.tools.timetool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tbosch.tools.timetool.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	public Project findByActiveTrue();

}
