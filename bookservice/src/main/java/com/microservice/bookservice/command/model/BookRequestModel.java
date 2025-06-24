package com.microservice.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestModel {

	private String id;
	
	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, message = "Name must be >= 2 characters")
    private String name;
	
	@NotBlank(message = "Author is mandatory")
    private String author;
    private Boolean isReady;
}
