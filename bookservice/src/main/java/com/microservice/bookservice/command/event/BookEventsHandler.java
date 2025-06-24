package com.microservice.bookservice.command.event;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservice.bookservice.command.data.Book;
import com.microservice.bookservice.command.data.BookRepository;
import com.microservice.commonservice.event.BookRollbackStatusEvent;
import com.microservice.commonservice.event.BookUpdatedStatusEvent;

@Component
public class BookEventsHandler {

	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookUpdatedEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getId());
		oldBook.ifPresent(book -> {
			book.setName(event.getName());
			book.setAuthor(event.getAuthor());
			book.setIsReady(event.getIsReady());
			bookRepository.save(book);
		});

	}

	@EventHandler
	public void on(BookDeletedEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getId());
		oldBook.ifPresent(book -> bookRepository.delete((book)));
	}

	@EventHandler
	public void on(BookUpdatedStatusEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getBookId());
		oldBook.ifPresent(book -> {
			book.setIsReady(event.isReady());
			bookRepository.save(book);
		});

	}
	
	@EventHandler
	public void on(BookRollbackStatusEvent event) {
		Optional<Book> oldBook = bookRepository.findById(event.getBookId());
		oldBook.ifPresent(book -> {
			book.setIsReady(event.isReady());
			bookRepository.save(book);
		});
	}
}
