package com.microservice.employeeservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.employeeservice.command.command.CreateEmployeeCommand;
import com.microservice.employeeservice.command.command.DeleteEmployeeCommand;
import com.microservice.employeeservice.command.command.UpdateEmployeeCommand;
import com.microservice.employeeservice.command.event.EmployeeCreatedEvent;
import com.microservice.employeeservice.command.event.EmployeeDeletedEvent;
import com.microservice.employeeservice.command.event.EmployeeUpdatedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class EmployeeAggregate {
	@AggregateIdentifier
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	private String Kin;
	
	private Boolean isDisciplined;
	
	@CommandHandler
	public EmployeeAggregate(CreateEmployeeCommand command) {
		EmployeeCreatedEvent event = new EmployeeCreatedEvent();
		BeanUtils.copyProperties(command, event);
		//dispatch event
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(UpdateEmployeeCommand command) {
		EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
    public void handle(DeleteEmployeeCommand command){
        EmployeeDeletedEvent event = new EmployeeDeletedEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);
    }
	
	@EventSourcingHandler
	public void on(EmployeeCreatedEvent event) {
		this.id = event.getId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.Kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}
	
	@EventSourcingHandler
    public void on(EmployeeUpdatedEvent event){
        this.id = event.getId();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.Kin = event.getKin();
        this.isDisciplined = event.getIsDisciplined();
    }
	
	@EventSourcingHandler
    public void on(EmployeeDeletedEvent event){
        this.id = event.getId();
    }
}
