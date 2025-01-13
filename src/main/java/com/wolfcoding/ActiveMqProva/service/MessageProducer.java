package com.wolfcoding.ActiveMqProva.service;

import com.wolfcoding.ActiveMqProva.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private static final String QUEUE_NAME = "test.queue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
        System.out.println("Messaggio inviato alla coda: " + message);
    }


}
