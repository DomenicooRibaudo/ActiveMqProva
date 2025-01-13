package com.wolfcoding.ActiveMqProva.service;

import com.wolfcoding.ActiveMqProva.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

public class PersonProducer {
    private static final String QUEUE_PERSON = "test.person";

    private JmsTemplate jmsTemplate;

    public PersonProducer(JmsTemplate jmsTemplate) {
    }

    public void sendPerson(Person person) {
        System.out.println("Invio oggetto Person: " + person + " alla coda: " + QUEUE_PERSON);
        jmsTemplate.convertAndSend(QUEUE_PERSON, person);
        System.out.println("Oggetto Person inviato alla coda: " + person);
    }
}
