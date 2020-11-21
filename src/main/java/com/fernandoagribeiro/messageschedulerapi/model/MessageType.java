package com.fernandoagribeiro.messageschedulerapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    public abstract void send(ScheduledMessage message);

    public void validateRecipient(String recipient) throws IllegalArgumentException {
        if (recipient == null || recipient.isBlank()) {
            throw new IllegalArgumentException("It's mandatory to inform the message's recipient");
        }
    }
}
