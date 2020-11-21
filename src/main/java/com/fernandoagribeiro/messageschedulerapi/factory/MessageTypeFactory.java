package com.fernandoagribeiro.messageschedulerapi.factory;

import com.fernandoagribeiro.messageschedulerapi.model.MessageType;

public interface MessageTypeFactory {
    MessageType createMessageType(String messageType);
}
