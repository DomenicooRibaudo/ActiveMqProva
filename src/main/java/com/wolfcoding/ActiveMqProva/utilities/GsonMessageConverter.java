package com.wolfcoding.ActiveMqProva.utilities;

import com.google.gson.Gson;
import com.wolfcoding.ActiveMqProva.Person;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
public class GsonMessageConverter implements MessageConverter {

    private final Gson gson = new Gson();

    @Override
    public Message toMessage(Object object, Session session) throws JMSException {
        String payload = gson.toJson(object);
        return session.createTextMessage(payload);
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getText();
            return gson.fromJson(payload, Person.class);
        }
        throw new MessageConversionException("Messaggio non supportato: " + message);
    }
}
