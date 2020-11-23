package com.fernandoagribeiro.messageschedulerapi.repository;

import com.fernandoagribeiro.messageschedulerapi.model.ScheduledMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledMessageRepository extends CrudRepository<ScheduledMessage, Long> {
}
