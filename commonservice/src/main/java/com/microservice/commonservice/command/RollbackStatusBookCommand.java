package com.microservice.commonservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackStatusBookCommand {
	
	@TargetAggregateIdentifier
	private String bookId;
	private boolean isReady;
	private String employeeId;
	private String borrowingId;

}
