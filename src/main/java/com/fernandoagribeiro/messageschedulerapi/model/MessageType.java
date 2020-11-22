package com.fernandoagribeiro.messageschedulerapi.model;

import com.fernandoagribeiro.messageschedulerapi.enumeration.MessageTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@Entity
@Table(name = "message_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type")
public abstract class MessageType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int messageTypeId;

    @Column(name = "message_type", insertable = false, updatable = false)
    private String messageTypeName;

    @OneToMany(mappedBy = "messageType")
    private List<ScheduledMessage> scheduledMessageList;

    @Transient
    private MessageTypeEnum messageTypeEnum;

    public abstract void send(ScheduledMessage message);

    /*
        The default validation evaluates whether or not the recipient is a valid cell phone number, as Message Types
        that rely on this kind of recipient are more common
    */
    public void validateRecipient(String recipient) throws IllegalArgumentException {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("It's mandatory to inform the message's recipient");
        }

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
