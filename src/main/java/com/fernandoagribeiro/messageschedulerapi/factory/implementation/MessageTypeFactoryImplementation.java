package com.fernandoagribeiro.messageschedulerapi.factory.implementation;

import com.fernandoagribeiro.messageschedulerapi.factory.MessageTypeFactory;
import com.fernandoagribeiro.messageschedulerapi.model.*;

public class MessageTypeFactoryImplementation implements MessageTypeFactory {

    @Override
    public MessageType createMessageType(String messageType) {
        //I've opted for the Simple Factory pattern just for the sake of being simple
        switch (messageType){
            case "WhatsApp":
                return new WhatsAppMessageType();
            case "SMS":
                return new SMSMessageType();
            case "Push":
                return new PushNotificationMessageType();
            default:
                return new EmailMessageType();
        }
    }
}
