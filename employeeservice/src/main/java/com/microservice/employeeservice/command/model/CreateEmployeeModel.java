package com.microservice.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeModel {
	
	@NotBlank(message = "First name is mandatory")
	private String firstName;
	
	@NotBlank(message = "Last name is mandatory")
	private String lastName;
	
	@NotBlank(message = "Kin name is mandatory")
	private String Kin;
	
}
