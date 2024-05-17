package com.isep.appli.services;

import com.isep.appli.dbModels.Message;
import com.isep.appli.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> getMessagesBySenderIdAndDestinationId(Long sender, Long destination) {
        return messageRepository.findBySenderIdAndDestinationId(sender, destination);
    }

    public List<Message> getMessagesByDiscussion(Long discussion) {
        return messageRepository.findByDiscussion(discussion);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}