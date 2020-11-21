package com.fernandoagribeiro.messageschedulerapi.factory.implementation;

import com.fernandoagribeiro.messageschedulerapi.factory.ScheduledMessageFactory;
import com.fernandoagribeiro.messageschedulerapi.model.*;

public class ScheduledMessageFactoryImplementation implements ScheduledMessageFactory {

    @Override
    public ScheduledMessage createMessage(String messageType) {
        //I've opted for the Simple Factory pattern just for the sake of being simple
        switch (messageType){
            case "WhatsApp":
                return new ScheduledMessage(new WhatsAppMessageType());
            case "SMS":
                return new ScheduledMessage(new SMSMessageType());
            case "Push":
                return new ScheduledMessage(new PushNotificationMessageType());
            default:
                return new ScheduledMessage(new EmailMessageType());
        }
    }
}
