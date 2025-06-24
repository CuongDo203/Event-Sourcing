package com.microservice.bookservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseModel {
	
	private String id;
    private String name;
    private String author;
    private Boolean isReady;
}
