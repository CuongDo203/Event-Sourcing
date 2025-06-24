package com.microservice.employeeservice.command.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeCommand {

	@TargetAggregateIdentifier
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String Kin;
	
	private Boolean isDisciplined;
}
