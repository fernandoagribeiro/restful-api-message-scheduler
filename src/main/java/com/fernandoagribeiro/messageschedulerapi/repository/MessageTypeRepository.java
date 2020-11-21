package com.fernandoagribeiro.messageschedulerapi.repository;

import com.fernandoagribeiro.messageschedulerapi.model.MessageType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessageTypeRepository extends CrudRepository<MessageType, Integer> {
    Optional<MessageType> findByMessageTypeName(String messageTypeName);
}
