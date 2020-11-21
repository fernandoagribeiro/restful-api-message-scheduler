package com.fernandoagribeiro.messageschedulerapi.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity(name = "WhatsApp")
@DiscriminatorValue("WhatsApp")
public class WhatsAppMessageType extends MessageType {
    @Override
    public void send(ScheduledMessage message) {
        //TODO: implement the method responsible to send a WhatsApp message
    }

    @Override
    public void validateRecipient(String recipient) throws IllegalArgumentException {
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$"
                + "|^(\\(?\\d{2}\\)?\\s)?(\\d{4,5}\\-\\d{4})";

        Pattern pattern = Pattern.compile(patterns);

        Matcher matcher = pattern.matcher(recipient);

        if (!matcher.matches())
            throw new IllegalArgumentException("The cell phone number that was provided is invalid");
    }
}
