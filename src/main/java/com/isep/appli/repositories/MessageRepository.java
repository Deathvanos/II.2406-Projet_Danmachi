package com.isep.appli.repositories;

import com.isep.appli.dbModels.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndDestinationId(Long sender, Long destination);
    List<Message> findByDiscussion(Long discussion);
    Message findMessageById(Long id);
}