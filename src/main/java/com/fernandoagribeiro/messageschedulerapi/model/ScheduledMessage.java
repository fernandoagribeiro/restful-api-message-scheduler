package com.fernandoagribeiro.messageschedulerapi.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "scheduled_message")
public class ScheduledMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "luiza_labs_exam.scheduled_message_id_seq")
    @Column(name = "id")
    private long id;

    @Column(name = "message_content")
    private String messageContent;

    @ManyToOne
    @JoinColumn(name="message_type_id")
    private MessageType messageType;

    @Column(name = "recipient")
    private String recipient;

    @Column(name = "date_to_send")
    private LocalDateTime dateToSend;

    @Column(name = "is_sent")
    @ColumnDefault(value = "false")
    private boolean isSent;

    public ScheduledMessage(MessageType messageType) {
        this.messageType = messageType;
    }

    public void send() {
        this.messageType.send(this);
    }

    public void validate() throws IllegalArgumentException {
        if (this.messageContent == null || this.messageContent.isBlank())
            throw new IllegalArgumentException("It's mandatory to provide the content of the message");

        if (this.messageType == null)
            throw new IllegalArgumentException("It's mandatory to inform the message type");

        if (this.dateToSend == null)
            throw new IllegalArgumentException("It's mandatory to inform the scheduled date to send the message");

        if (this.dateToSend.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("The scheduled date to send the message cannot be set in the past");

        if (this.isSent)
            throw new IllegalArgumentException("It's not possible to create a message with its status being set as it" +
                    "has already been sent");

        this.messageType.validateRecipient(this.recipient);
    }
}
