package com.fernandoagribeiro.messageschedulerapi.controller.v1;

import com.fernandoagribeiro.messageschedulerapi.DTO.ScheduledMessageDTO;
import com.fernandoagribeiro.messageschedulerapi.DTO.StandardResponseDTO;
import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import com.fernandoagribeiro.messageschedulerapi.factory.MessageTypeFactory;
import com.fernandoagribeiro.messageschedulerapi.mapping.ScheduledMessageMapper;
import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import com.fernandoagribeiro.messageschedulerapi.service.ScheduledMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/messagescheduler/v1/messages")
@Slf4j
public class ScheduledMessageController {

    private final ScheduledMessageService scheduledMessageService;
    private MessageTypeFactory messageTypeFactory;

    @Autowired
    public ScheduledMessageController(ScheduledMessageService scheduledMessageService, MessageTypeFactory messageTypeFactory) {
        this.scheduledMessageService = scheduledMessageService;
        this.messageTypeFactory = messageTypeFactory;
    }

    private void validateMessageDTO(ScheduledMessageDTO scheduledMessageDTO) throws IllegalArgumentException {
        if (scheduledMessageDTO.getDateToSend().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The scheduled date to send the message cannot be set in the past");
        }

        if (scheduledMessageDTO.isSent()) {
            throw new IllegalArgumentException("It's not possible to create a message with its status being set as it" +
                    " has already been sent");
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<StandardResponseDTO> create(@RequestBody @Valid ScheduledMessageDTO scheduledMessageDTO) {
        try {
            log.info("Ensuring that the chosen Message Type " + scheduledMessageDTO.getMessageTypeName() + " exists");
            if (!MessageTypeEnum.contains(scheduledMessageDTO.getMessageTypeName())) {
                var errorMessage = "The Message Type " + scheduledMessageDTO.getMessageTypeName() + " was not found";

                log.error(errorMessage);

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponseDTO(errorMessage, null));
            }

            log.info("Validating the Scheduled Message");
            this.validateMessageDTO(scheduledMessageDTO);
            log.info("The Scheduled Message is valid");

            log.info("Instantiating a Message Type");
            var messageType = messageTypeFactory.findMessageType(MessageTypeEnum.valueOf(
                    scheduledMessageDTO.getMessageTypeName()));

            log.info("Converting the object DTO into the persistence model");
            ScheduledMessage scheduledMessage = ScheduledMessageMapper.INSTANCE.toScheduledMessage(scheduledMessageDTO);
            scheduledMessage.setMessageType(messageType);

            var newScheduleMessage = this.scheduledMessageService.save(scheduledMessage);

            var successMessage = "Scheduled Message successfully saved";
            log.info(successMessage);

            return ResponseEntity.ok().body(new StandardResponseDTO(successMessage,
                ScheduledMessageMapper.INSTANCE.toDTO(newScheduleMessage, scheduledMessage.getMessageType())));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new StandardResponseDTO(e.getMessage(), null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardResponseDTO(e.getMessage(),
                    null));
        }
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<StandardResponseDTO> find(@PathVariable Long id) {
        try {
            if (id < 1)
                throw new IllegalArgumentException("The Scheduled Message Id should be higher than zero");

            var scheduledMessage = scheduledMessageService.findById(id);

            if (scheduledMessage.isPresent()) {
                log.info("A Scheduled Message was found and it will now be converted to its DTO equivalent");
                var scheduledMessageDTO = ScheduledMessageMapper.INSTANCE.toDTO(scheduledMessage.get(),
                        scheduledMessage.get().getMessageType());

                log.info("Returning the DTO object in the Response Body");
                return ResponseEntity.ok().body(new StandardResponseDTO("Found", scheduledMessageDTO));
            } else {
                var errorMessage = "A scheduled message with the Id " + id + " was not found";
                log.error(errorMessage);

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardResponseDTO(errorMessage, null));
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new StandardResponseDTO(e.getMessage(), null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardResponseDTO(e.getMessage(),
                    null));
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<StandardResponseDTO> delete(@PathVariable Long id) {
        try {
            if (id < 1)
                throw new IllegalArgumentException("The Scheduled Message Id should be higher than zero");

            log.info("Preparing to delete the Scheduled Message");
            scheduledMessageService.deleteById(id);

            var successMessage = "The Scheduled Message was successfully deleted";
            log.info(successMessage);
            return ResponseEntity.ok().body(new StandardResponseDTO(successMessage, null));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(new StandardResponseDTO(e.getMessage(), null));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardResponseDTO(e.getMessage(),
                    null));
        }
    }
}
