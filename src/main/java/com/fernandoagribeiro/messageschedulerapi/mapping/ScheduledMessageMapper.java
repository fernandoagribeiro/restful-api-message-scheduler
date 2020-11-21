package com.fernandoagribeiro.messageschedulerapi.mapping;

import com.fernandoagribeiro.messageschedulerapi.DTO.ScheduledMessageDTO;
import com.fernandoagribeiro.messageschedulerapi.model.MessageType;
import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScheduledMessageMapper {
    ScheduledMessageMapper INSTANCE = Mappers.getMapper(ScheduledMessageMapper.class);

    @Mapping(source = "messageType.messageTypeName", target = "messageTypeName")
    ScheduledMessageDTO toDTO(ScheduledMessage scheduledMessage, MessageType messageType);

    ScheduledMessage toScheduledMessage(ScheduledMessageDTO scheduledMessageDTO);
}
