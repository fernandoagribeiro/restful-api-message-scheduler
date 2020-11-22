package com.fernandoagribeiro.messageschedulerapi.model;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Push")
@DiscriminatorValue("Push")
@Component
public class PushNotificationMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send a Push Notification message
    }

    @Override
    public MessageTypeEnum getMessageTypeEnum() {
        return MessageTypeEnum.Push;
    }
}
