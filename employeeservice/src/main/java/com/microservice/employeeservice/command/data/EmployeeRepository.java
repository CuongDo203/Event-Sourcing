package com.microservice.employeeservice.command.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>{
	
	List<Employee> findAllByIsDisciplined(Boolean isDisciplined);
	
}
