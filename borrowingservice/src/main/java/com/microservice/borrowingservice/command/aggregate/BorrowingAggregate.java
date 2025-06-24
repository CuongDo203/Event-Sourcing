package com.microservice.borrowingservice.command.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.borrowingservice.command.command.CreateBorrowingCommand;
import com.microservice.borrowingservice.command.command.DeleteBorrowingCommand;
import com.microservice.borrowingservice.command.event.BorrowingCreatedEvent;
import com.microservice.borrowingservice.command.event.BorrowingDeletedEvent;
import com.microservice.commonservice.command.RollbackStatusBookCommand;
import com.microservice.commonservice.event.BookRollbackStatusEvent;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Aggregate
public class BorrowingAggregate {
	
	@AggregateIdentifier
	private String id;
	
	private String bookId;
	
	private String employeeId;
	private boolean isReady;
	
	private Date borrowingDate;
	
	private Date returnDate;
	
	public BorrowingAggregate() {}
	
	@CommandHandler
	public BorrowingAggregate(CreateBorrowingCommand command) {
		BorrowingCreatedEvent event = new BorrowingCreatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);  //xu ly command se phat di mot event
	}
	
	@CommandHandler
	public void handle(DeleteBorrowingCommand command) {
		BorrowingDeletedEvent event = new BorrowingDeletedEvent(command.getId());
		AggregateLifecycle.apply(event);
	}
	
	@CommandHandler
	public void handle(RollbackStatusBookCommand command) {
		BookRollbackStatusEvent event = new BookRollbackStatusEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(BookRollbackStatusEvent event) {
		this.id = event.getBookId();
		this.isReady = event.isReady();
	}
	
	@EventSourcingHandler
	public void on(BorrowingCreatedEvent event) {
		this.id = event.getId();
		this.bookId = event.getBookId();
		this.employeeId = event.getEmployeeId();
		this.borrowingDate = event.getBorrowingDate();
	}
	
	@EventSourcingHandler
	public void on(BorrowingDeletedEvent event) {
		this.id = event.getId();
	}
}
