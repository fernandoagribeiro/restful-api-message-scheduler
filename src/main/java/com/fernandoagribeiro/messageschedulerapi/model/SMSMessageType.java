package com.fernandoagribeiro.messageschedulerapi.model;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SMS")
@DiscriminatorValue("SMS")
@Component
public class SMSMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send a SMS message
    }

    @Override
    public MessageTypeEnum getMessageTypeEnum() {
        return MessageTypeEnum.SMS;
    }
}
