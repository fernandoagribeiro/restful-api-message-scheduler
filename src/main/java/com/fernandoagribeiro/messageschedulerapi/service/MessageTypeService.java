package com.fernandoagribeiro.messageschedulerapi.service;

import com.fernandoagribeiro.messageschedulerapi.model.MessageType;
import com.fernandoagribeiro.messageschedulerapi.repository.MessageTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageTypeService {
    private MessageTypeRepository messageTypeRepository;

    @Autowired
    public MessageTypeService(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    public Optional<MessageType> findByMessageTypeName(String messageTypeName) {
        return this.messageTypeRepository.findByMessageTypeName(messageTypeName);
    }
}
