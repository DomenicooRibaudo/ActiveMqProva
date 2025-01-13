package com.wolfcoding.ActiveMqProva.service;

import com.wolfcoding.ActiveMqProva.Person;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private static final String QUEUE_NAME = "test.queue";

    @JmsListener(destination = QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Messaggio ricevuto dalla coda: " + message);
    }

    @JmsListener(destination = QUEUE_NAME)
    public void receivePerson(Person person) {
        System.out.println("Oggetto Person ricevuto dalla coda: " + person);
    }
}
