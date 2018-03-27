package com.praveen.shethe.JMSExample.service;

import com.praveen.shethe.JMSExample.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class MessageService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    private Logger logger = Logger.getLogger("Message Service Controller");

    @JmsListener(destination = "messageQueue")
    public void receiveMessage(Student student) {
        student.setName("Praveenkumar Shethe");
        Map<String, Object> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        jmsMessagingTemplate.convertAndSend("messageQueue", student, header);
    }

    @GetMapping("/jmstest")
    public void sendRequest() {
        Student student = new Student();
        student.setName("Hellow");
        Map<String, Object> header = new HashMap<>();
        header.put("replyTo", "replyQ");
        logger.info("<-----------------Send Request---------------->");
        jmsMessagingTemplate.convertAndSend("messageQueue", student, header);
    }

    @JmsListener(destination = "messageQueue")
    public void receiveResponse(Student student) {
        logger.info("Student " + student.getName());
        System.out.println("Response Student : " + student.toString());
    }
}
