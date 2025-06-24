package com.microservice.notificationservice.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.errors.RetriableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import com.microservice.commonservice.services.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventConsumer {

	@Autowired()
	private EmailService emailService;
	
	@KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
	@RetryableTopic(
			attempts = "4",   //3 topic retry 1 topic DLQ,
			backoff = @Backoff(delay = 1000, multiplier = 2),  //1s, 2s, 4s
			autoCreateTopics = "true",
			dltStrategy = DltStrategy.FAIL_ON_ERROR,     
			include = {RetriableException.class, RuntimeException.class}  //define cac exception se retry
			)
	public void listen(String message) {
		log.info("Received message: "+ message);
		
		throw new RuntimeException("Error test");
	}
	
	@DltHandler
	void processDltMessage(@Payload String message) {
		log.info("DLT receive message: "+ message);
	}
	
	@KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
	public void testEmail(String message) {
		log.info("Receive message");
		String template = "<div>\n"
				+ "<h1>Welcome, %s!</h1>\n"
				+ "<p>Thanks for joining us. We're excited to have you on board.</p>\n"
				+ "<p>Your username is: <strong>%s</strong></p>"
				+ "</div>";
		String filledTemplate = String.format(template, "Cuong do", message);
		emailService.sendEmail(message, "Thanks for buy my books", filledTemplate, true, null);
	}
	
	@KafkaListener(topics = "emailTemplate", containerFactory = "kafkaListenerContainerFactory")
	public void emailTemplate(String message) {
		log.info("Receive message"+ message);
		Map<String, Object> placeholders = new HashMap<>();
		placeholders.put("name", "Microservice voi springboot");
		emailService.sendEmailWithTemplate(message,	"Welcome to my store", "emailTemplate.ftl", 
				placeholders, null);
	}
}
