package com.fernandoagribeiro.messageschedulerapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduledMessageDTO {
    private long id;

    @NotBlank(message = "It's mandatory to inform the content of the message")
    @Size(max = 600)
    private String messageContent;

    @NotBlank(message = "")
    //TODO: Create a enum and try to instantiate based on the enum
    private String messageTypeName;

    @NotBlank(message = "It's mandatory to inform the message's recipient")
    private String recipient;

    @NotNull (message = "It's mandatory to inform the scheduled date to send the message")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateToSend;

    private boolean isSent;
}