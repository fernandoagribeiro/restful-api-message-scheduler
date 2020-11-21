package com.fernandoagribeiro.messageschedulerapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageTypeDTO {
    private int messageTypeId;
    private String messageTypeName;
}
