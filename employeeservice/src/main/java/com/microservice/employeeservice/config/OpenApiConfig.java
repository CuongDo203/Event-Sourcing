package com.microservice.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Employee Api Spectification - Microservice",
				description = "Api documentation for Employee service",
				version = "1.0",
				contact = @Contact(
						name = "Cuong do"
				),
				license = @License(
						name = "MIT License"
				)
				
		),
		servers = {
				@Server(
						description = "Local ENV",
						url = "http://localhost:9002"
				),
				@Server(
						description = "Dev ENV",
						url = "http://employee-service.dev.com"
				)
		}
)
public class OpenApiConfig {

}
