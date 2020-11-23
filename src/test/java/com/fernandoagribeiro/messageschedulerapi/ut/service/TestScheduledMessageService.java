package com.fernandoagribeiro.messageschedulerapi.ut.service;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import com.fernandoagribeiro.messageschedulerapi.model.EmailMessageType;
import com.fernandoagribeiro.messageschedulerapi.model.MessageType;
import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import com.fernandoagribeiro.messageschedulerapi.repository.MessageTypeRepository;
import com.fernandoagribeiro.messageschedulerapi.repository.ScheduledMessageRepository;
import com.fernandoagribeiro.messageschedulerapi.service.ScheduledMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestScheduledMessageService {
    @Mock
    ScheduledMessageRepository scheduledMessageRepository;
    @Mock
    MessageTypeRepository messageTypeRepository;
    @InjectMocks
    ScheduledMessageService service;

    @Test
    public void givenValidMessage_whenSaveMessage_thenSucceed() {
        MessageType messageType = new EmailMessageType();
        messageType.setMessageTypeName("Email");
        messageType.setMessageTypeEnum(MessageTypeEnum.Email);
        ScheduledMessage scheduledMessage = new ScheduledMessage(messageType);
        scheduledMessage.setId(1);
        scheduledMessage.setDateToSend(LocalDateTime.now().plusHours(1));
        scheduledMessage.setMessageContent("Message Content Test");
        scheduledMessage.setRecipient("fernandoagribeiro@gmail.com");

        when(messageTypeRepository.findByMessageTypeName("Email")).thenReturn(Optional.of(messageType));
        when(scheduledMessageRepository.save(scheduledMessage)).thenReturn(scheduledMessage);

        Assertions.assertTrue(service.save(scheduledMessage).getId() > 0);

        verify(scheduledMessageRepository, atMostOnce()).save(any(ScheduledMessage.class));
        verify(messageTypeRepository, atMostOnce()).findByMessageTypeName("Email");
    }
}
