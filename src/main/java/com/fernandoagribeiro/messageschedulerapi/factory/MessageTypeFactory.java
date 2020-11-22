package com.fernandoagribeiro.messageschedulerapi.factory;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import com.fernandoagribeiro.messageschedulerapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MessageTypeFactory {

    private Map<MessageTypeEnum, MessageType> messageTypes;

    @Autowired
    public MessageTypeFactory(Set<MessageType> messageTypeSet) {
          createMessageType(messageTypeSet);
    }

    public MessageType findMessageType(MessageTypeEnum messageTypeEnum) {
        var messageType = messageTypes.get(messageTypeEnum);

        messageType.setMessageTypeId(messageTypeEnum.messageTypeValue);
        messageType.setMessageTypeName(messageTypeEnum.name());

        return messageType;
    }

    private void createMessageType(Set<MessageType> messageTypeSet) {
        messageTypes = new HashMap<>();
        messageTypeSet.forEach(messageType -> messageTypes.put(messageType.getMessageTypeEnum(), messageType));
    }
}
