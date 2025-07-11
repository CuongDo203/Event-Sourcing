package com.microservice.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.microservice.bookservice.command.command.CreateBookCommand;
import com.microservice.bookservice.command.command.DeleteBookCommand;
import com.microservice.bookservice.command.command.UpdateBookCommand;
import com.microservice.bookservice.command.event.BookCreatedEvent;
import com.microservice.bookservice.command.event.BookDeletedEvent;
import com.microservice.bookservice.command.event.BookUpdatedEvent;
import com.microservice.commonservice.command.UpdateStatusBookCommand;
import com.microservice.commonservice.event.BookUpdatedStatusEvent;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BookAggregate {

	@AggregateIdentifier
	private String id;

	private String name;

	private String author;

	private boolean isReady;

	@CommandHandler
	public BookAggregate(CreateBookCommand command) {
		BookCreatedEvent bookCreateEvent = new BookCreatedEvent();
		BeanUtils.copyProperties(command, bookCreateEvent);

		// publish event
		AggregateLifecycle.apply(bookCreateEvent);
	}

	@CommandHandler
	public void handle(UpdateBookCommand command) {
		BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();
		BeanUtils.copyProperties(command, bookUpdatedEvent);
		AggregateLifecycle.apply(bookUpdatedEvent);
	}

	@CommandHandler
	public void handle(DeleteBookCommand command) {
		BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
		BeanUtils.copyProperties(command, bookDeletedEvent);
		AggregateLifecycle.apply(bookDeletedEvent);
	}

	@CommandHandler
	public void handle(UpdateStatusBookCommand command) {
		BookUpdatedStatusEvent bookUpdatedStatusEvent = new BookUpdatedStatusEvent();
		BeanUtils.copyProperties(command, bookUpdatedStatusEvent);
		AggregateLifecycle.apply(bookUpdatedStatusEvent);
	}
	
	@EventSourcingHandler
	public void on(BookCreatedEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		this.author = event.getAuthor();
		this.isReady = event.isReady();
	}

	@EventSourcingHandler
	public void on(BookUpdatedEvent event) {
		this.id = event.getId();
		this.name = event.getName();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
	}

	@EventSourcingHandler
	public void on(BookDeletedEvent event) {
		this.id = event.getId();
	}
	
	@EventSourcingHandler
	public void on(BookUpdatedStatusEvent event) {
		this.id = event.getBookId();
		this.isReady = event.isReady();
	}
}
