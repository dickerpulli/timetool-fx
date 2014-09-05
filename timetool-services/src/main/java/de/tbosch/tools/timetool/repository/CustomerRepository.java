package de.tbosch.tools.timetool.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.tbosch.tools.timetool.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
