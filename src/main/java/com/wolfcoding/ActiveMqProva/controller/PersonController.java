package com.wolfcoding.ActiveMqProva.controller;

import com.wolfcoding.ActiveMqProva.ObjectFactory;
import com.wolfcoding.ActiveMqProva.Person;
import com.wolfcoding.ActiveMqProva.service.PersonProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final ObjectFactory objectFactory;

    private PersonProducer personProducer;

    public PersonController() {
        this.objectFactory = new ObjectFactory();
    }

    @PostMapping("/send")
    public String sendPersonToQueue(@RequestParam String name, @RequestParam String email, @RequestParam int id) {
        // Creazione di un oggetto Person
        Person person = objectFactory.createPerson();
        person.setId(id);
        person.setName(name);
        person.setEmail(email);

        // Invia il messaggio alla coda
        personProducer.sendPerson(person);

        return "Oggetto Person inviato alla coda test.person!";
    }
}
