package com.fernandoagribeiro.messageschedulerapi.service;

import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import com.fernandoagribeiro.messageschedulerapi.repository.MessageTypeRepository;
import com.fernandoagribeiro.messageschedulerapi.repository.ScheduledMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.util.Optional;

@Slf4j
@Service
public class ScheduledMessageService {
    private ScheduledMessageRepository scheduledMessageRepository;
    private MessageTypeRepository messageTypeRepository;

    @Autowired
    public ScheduledMessageService(ScheduledMessageRepository scheduledMessageRepository, MessageTypeRepository messageTypeRepository) {
        this.scheduledMessageRepository = scheduledMessageRepository;
        this.messageTypeRepository = messageTypeRepository;
    }

    public void save(@NonNull ScheduledMessage scheduledMessage) {
        try {
            if (scheduledMessage.getMessageType() == null)
                throw new IllegalArgumentException("It's necessary to inform the Schedule Message's Type");

            var messageType = messageTypeRepository.findByMessageTypeName(scheduledMessage.
                    getMessageType().getMessageTypeName());

            if (!messageType.isPresent())
                throw new IllegalArgumentException("The Message Type " + scheduledMessage.getMessageType().getMessageTypeName() +
                        " was not found");

            scheduledMessage.setMessageType(messageType.get());

            log.info("Validating the Scheduled Message");
            scheduledMessage.validate();

            log.info("The Scheduled Message is valid, preparing to save");
            scheduledMessageRepository.save(scheduledMessage);

            log.info("Scheduled Message successfully saved");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Optional<ScheduledMessage> findScheduledMessageById(@Min(value = 1, message = "The Scheduled Message Id should be higher than zero") long id) {
        try {
            log.info("Searching for a Scheduled Message with the Id " + id);

            return scheduledMessageRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public void deleteById(@Min(value = 1, message = "The Scheduled Message Id should be higher than zero") long id) {
        try {
            log.info("Preparing to delete the Scheduled Message with the Id " + id);

            scheduledMessageRepository.deleteById(id);

            log.info("The Scheduled Message was successfully deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }
}
