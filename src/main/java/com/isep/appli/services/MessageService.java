package com.isep.appli.services;

import com.isep.appli.dbModels.Message;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.models.FormattedMessage;
import com.isep.appli.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final PersonnageService personnageService;

    MessageService(MessageRepository messageRepository, PersonnageService personnageService) {
        this.messageRepository = messageRepository;
        this.personnageService = personnageService;
    }

    public Optional<Message> getById(Long id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessageById(long id) {
        Optional<Message> messageOptional = getById(id);
        Message message = messageOptional.orElse(null);
        messageRepository.delete(message);
    }

    public String displayDestination(Personnage personnage) {
        return personnage.getFirstName() + " " + personnage.getLastName();
    }

    public String formatDate(Date date) {
        if (date == null) {
            return " ";
        }
        Date now = new Date();
        SimpleDateFormat simpleDateFormat;
        if (now.getDate() == date.getDate() && now.getMonth() == date.getMonth() && now.getYear() == date.getYear()) {
            simpleDateFormat = new SimpleDateFormat("HH:mm");
        }
        else {
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }
        return simpleDateFormat.format(date);
    }

    public List<Message> getMessagesByDiscussion(Long discussion) {
        return messageRepository.findByDiscussion(discussion);
    }

    public List<FormattedMessage> getFormattedMessagesByDiscussionId(Long discussion, Personnage personnage) {
        List<Message> messages = getMessagesByDiscussion(discussion);
        Collections.sort(messages, Comparator.comparing(Message::getDate));
        List<FormattedMessage> formattedMessages = new ArrayList();
        for (Message message : messages) {
            FormattedMessage formattedMessage = new FormattedMessage (
                    message.getId(),
                    message.getContent(),
                    displayDestination(personnageService.getPersonnageById(message.getSenderId())),
                    formatDate(message.getDate()),
                    !message.getSenderId().equals(personnage.getId())
            );
            formattedMessages.add(formattedMessage);
        }
        return formattedMessages;
    }
}