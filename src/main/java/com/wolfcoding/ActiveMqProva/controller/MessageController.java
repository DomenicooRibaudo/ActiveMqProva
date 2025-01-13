package com.wolfcoding.ActiveMqProva.controller;

import com.wolfcoding.ActiveMqProva.ObjectFactory;
import com.wolfcoding.ActiveMqProva.Person;
import com.wolfcoding.ActiveMqProva.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageProducer messageProducer;

    private final ObjectFactory objectFactory;

    public MessageController() {
        this.objectFactory = new ObjectFactory();
    }

    @PostMapping("/create")
    public Person createPerson(@RequestParam String name, @RequestParam String email, @RequestParam int id) {
        Person person = objectFactory.createPerson();

        person.setId(id);
        person.setName(name);
        person.setEmail(email);

        System.out.println("Persona aggiunta con id:" + person.getId());
        return person;
    }



    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        messageProducer.sendMessage(message);
        return "Messaggio inviato: " + message;
    }
}
