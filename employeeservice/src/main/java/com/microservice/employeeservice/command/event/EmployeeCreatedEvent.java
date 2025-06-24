package com.microservice.employeeservice.command.event;

import com.microservice.employeeservice.command.command.CreateEmployeeCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCreatedEvent {
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String Kin;
	
	private Boolean isDisciplined;
}
