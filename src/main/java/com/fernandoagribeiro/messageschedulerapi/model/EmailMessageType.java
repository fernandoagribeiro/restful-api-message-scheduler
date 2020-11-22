package com.fernandoagribeiro.messageschedulerapi.model;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(name = "Email")
@DiscriminatorValue("Email")
@Component
public class EmailMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send an Email message
    }

    @Override
    public void validateRecipient(String recipient) throws IllegalArgumentException {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("It's mandatory to inform the message's recipient");
        }

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(recipient);

        if(!matcher.matches())
            throw new IllegalArgumentException("The recipient doesn't have a valid email");
    }

    @Override
    public MessageTypeEnum getMessageTypeEnum() {
        return MessageTypeEnum.Email;
    }
}
