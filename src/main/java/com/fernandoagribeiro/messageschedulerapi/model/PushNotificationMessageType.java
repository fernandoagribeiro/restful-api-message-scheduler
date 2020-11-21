package com.fernandoagribeiro.messageschedulerapi.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Push")
@DiscriminatorValue("Push")
public class PushNotificationMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send a Push Notification message
    }
}
