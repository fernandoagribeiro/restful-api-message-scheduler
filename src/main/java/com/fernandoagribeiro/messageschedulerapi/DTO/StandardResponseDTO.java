package com.fernandoagribeiro.messageschedulerapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StandardResponseDTO {
    private String message;
    private Object objToReturn;
}
