package com.microservice.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreatedEvent {

	private String id;
	
	private String name;
	
	private String author;
	
	private boolean isReady;
	
}
