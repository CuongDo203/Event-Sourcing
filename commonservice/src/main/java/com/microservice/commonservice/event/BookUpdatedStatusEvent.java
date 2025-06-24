package com.microservice.commonservice.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdatedStatusEvent {

	private String bookId;
	private boolean isReady;
	private String employeeId;
	private String borrowingId;
	
}
