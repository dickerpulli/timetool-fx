package de.tbosch.tools.timetool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tbosch.tools.timetool.model.Timeslot;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

	public Timeslot findByActiveTrue();

	public Timeslot findByProjectIdOrderByStarttimeDesc(long projectId);

}
