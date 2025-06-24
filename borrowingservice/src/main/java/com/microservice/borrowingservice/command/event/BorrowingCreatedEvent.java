package com.microservice.borrowingservice.command.event;

import java.util.Date;

import com.microservice.borrowingservice.command.command.CreateBorrowingCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowingCreatedEvent {
	
	private String id;
	
	private String bookId;
	
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;
	
}
