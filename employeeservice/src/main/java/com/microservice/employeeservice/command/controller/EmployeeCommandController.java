package com.microservice.employeeservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.employeeservice.command.command.CreateEmployeeCommand;
import com.microservice.employeeservice.command.command.DeleteEmployeeCommand;
import com.microservice.employeeservice.command.command.UpdateEmployeeCommand;
import com.microservice.employeeservice.command.model.CreateEmployeeModel;
import com.microservice.employeeservice.command.model.UpdateEmployeeModel;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping("")
	public String addEmployee(@Valid @RequestBody CreateEmployeeModel model) {
		CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(),
				model.getFirstName(), model.getLastName(), model.getKin(), false);
		return commandGateway.sendAndWait(command);
	}
	
	@PutMapping("/{id}")
	public String updateEmployee(@Valid @RequestBody UpdateEmployeeModel model,
			@PathVariable("id") String employeeId) {
		UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, 
				model.getFirstName(), model.getLastName(), model.getKin(), model.getIsDisciplined());
		return commandGateway.sendAndWait(command);
	}

	@Hidden
	@DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId){
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        return commandGateway.sendAndWait(command);
    }
}
