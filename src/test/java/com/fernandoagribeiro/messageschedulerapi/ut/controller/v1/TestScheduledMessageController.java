package com.fernandoagribeiro.messageschedulerapi.ut.controller.v1;

import com.fernandoagribeiro.messageschedulerapi.controller.v1.*;
import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import com.fernandoagribeiro.messageschedulerapi.factory.MessageTypeFactory;
import com.fernandoagribeiro.messageschedulerapi.model.EmailMessageType;
import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import com.fernandoagribeiro.messageschedulerapi.service.*;
import com.fernandoagribeiro.messageschedulerapi.DTO.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestScheduledMessageController {
    @InjectMocks
    private ScheduledMessageController scheduledMessageController;
    @Mock
    private ScheduledMessageService scheduledMessageService;
    @Mock
    MessageTypeFactory messageTypeFactory;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void givenValidMessage_whenSaveMessage_thenSucceed() {
        var scheduledMessageDTO = new ScheduledMessageDTO();
        scheduledMessageDTO.setDateToSend(LocalDateTime.now().plusHours(1));
        scheduledMessageDTO.setMessageContent("Message Content 35");
        scheduledMessageDTO.setMessageTypeName("Email");
        scheduledMessageDTO.setRecipient("fernandoagribeiro@gmail.com");

        var messageType = new EmailMessageType();
        messageType.setMessageTypeId(1);
        messageType.setMessageTypeName("Email");
        messageType.setMessageTypeEnum(MessageTypeEnum.Email);

        var scheduledMessage = new ScheduledMessage(messageType);
        scheduledMessage.setDateToSend(scheduledMessageDTO.getDateToSend());
        scheduledMessage.setMessageContent(scheduledMessageDTO.getMessageContent());
        scheduledMessage.setRecipient("fernandoagribeiro@gmail.com");

        var newScheduledMessage = new ScheduledMessage();
        newScheduledMessage.setId(1);
        newScheduledMessage.setDateToSend(scheduledMessage.getDateToSend());
        newScheduledMessage.setMessageContent(scheduledMessage.getMessageContent());
        newScheduledMessage.setMessageType(messageType);
        newScheduledMessage.setRecipient(scheduledMessage.getRecipient());

        when(scheduledMessageService.save(any(ScheduledMessage.class))).thenReturn(newScheduledMessage);

        Assertions.assertNotNull(scheduledMessageController.create(scheduledMessageDTO).getBody().getObjToReturn());

        verify(messageTypeFactory, atMostOnce()).findMessageType(MessageTypeEnum.Email);
        verify(scheduledMessageService, atMostOnce()).save(any(ScheduledMessage.class));
    }

    @Test
    public void givenInexistentMessageType_whenSaveMessage_thenBadRequest() {
        var scheduledMessageDTO = new ScheduledMessageDTO();
        scheduledMessageDTO.setDateToSend(LocalDateTime.now().plusHours(1));
        scheduledMessageDTO.setMessageContent("Message Content 1");
        scheduledMessageDTO.setMessageTypeName("aaaaa");
        scheduledMessageDTO.setRecipient("fernandoagribeiro@gmail.com");

        Assertions.assertEquals(scheduledMessageController.create(scheduledMessageDTO).getStatusCode(), HttpStatus.NOT_FOUND);

        verify(messageTypeFactory, never()).findMessageType(MessageTypeEnum.Email);
        verify(scheduledMessageService, never()).save(any(ScheduledMessage.class));
    }

    @Test
    public void givenDateSetInThePast_whenSaveMessage_thenBadRequest() {
        var scheduledMessageDTO = new ScheduledMessageDTO();
        scheduledMessageDTO.setDateToSend(LocalDateTime.now().minusHours(1));
        scheduledMessageDTO.setMessageContent("Message Content 1");
        scheduledMessageDTO.setMessageTypeName("Email");
        scheduledMessageDTO.setRecipient("fernandoagribeiro@gmail.com");

        Assertions.assertEquals(scheduledMessageController.create(scheduledMessageDTO).getStatusCode(), HttpStatus.BAD_REQUEST);

        verify(messageTypeFactory, never()).findMessageType(MessageTypeEnum.Email);
        verify(scheduledMessageService, never()).save(any(ScheduledMessage.class));
    }
}
