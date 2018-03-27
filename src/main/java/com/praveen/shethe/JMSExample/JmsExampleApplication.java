package com.praveen.shethe.JMSExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import com.rabbitmq.jms.admin.RMQConnectionFactory;

import javax.jms.ConnectionFactory;

@SpringBootApplication
public class JmsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsExampleApplication.class, args);
	}

	@Bean
	ConnectionFactory connectionFactory() {
		return new RMQConnectionFactory();
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setReceiveTimeout(2000);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
		return jmsTemplate;
	}


}
