package com.microservice.discoverserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoverserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoverserverApplication.class, args);
	}

}
