package com.fernandoagribeiro.messageschedulerapi.factory;

import com.fernandoagribeiro.messageschedulerapi.model.MessageType;
import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;

public interface ScheduledMessageFactory {
    ScheduledMessage createMessage(String type);
}
