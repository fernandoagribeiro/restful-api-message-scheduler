package com.fernandoagribeiro.messageschedulerapi.model;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(name = "WhatsApp")
@DiscriminatorValue("WhatsApp")
@Component
public class WhatsAppMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send a WhatsApp message
    }

    @Override
    public MessageTypeEnum getMessageTypeEnum() {
        return MessageTypeEnum.WhatsApp;
    }
}
