package com.javainuse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javainuse.model.Patient;

@Service
public class RabbitMQSender {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQSender.class);

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send(Patient patient) {
		amqpTemplate.convertAndSend("javainuse-direct-exchange", "javainuse", patient);
		logger.info("Send msg = " + patient);

	}
}