package com.isep.appli.services;

import com.isep.appli.dbModels.Discussion;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Message;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.models.FormattedDiscussion;
import com.isep.appli.repositories.DiscussionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final MessageService messageService;
    private final PersonnageService personnageService;
    @PersistenceContext
    private EntityManager entityManager;

    DiscussionService(DiscussionRepository discussionRepository, MessageService messageService, PersonnageService personnageService) {
        this.discussionRepository = discussionRepository;
        this.messageService = messageService;
        this.personnageService = personnageService;
    }

    public Optional<Discussion> getById(Long id) {
        return discussionRepository.findById(id);
    }

    public List<Discussion> getDiscussionsByPersonnage(Long personnage) {
        List<Discussion> discussions = discussionRepository.findByFirstPersonnageId(personnage);
        discussions.addAll(discussionRepository.findBySecondPersonnageId(personnage));
        return  discussions;
    }

    public List<Discussion> getDiscussionsByPersonnageAndFamilia(Long personnage, Long familia) {
        List<Discussion> discussions = discussionRepository.findByFirstPersonnageId(personnage);
        discussions.addAll(discussionRepository.findBySecondPersonnageId(personnage));
        List<Discussion> familiaDiscussion = discussionRepository.findByFamiliaId(familia);
        discussions.addAll(familiaDiscussion);
        return  discussions;
    }

    public Long getPrivateDestinationId(Discussion discussion, Personnage personnage) {
        Long destination;
        if (discussion.getFirstPersonnageId() == personnage.getId()) {
            destination = discussion.getSecondPersonnageId();
        }
        else {
            destination = discussion.getFirstPersonnageId();
        }
        return destination;
    }

    public Personnage getDestinationPersonnage(Discussion discussion, Personnage personnage) {
        Personnage destinationPersonnage;
        if (discussion.getConversationType().equals("PRIVATE")) {
            destinationPersonnage = personnageService.getPersonnageById(getPrivateDestinationId(discussion, personnage));
        }
        else {
            destinationPersonnage = personnageService.getPersonnageById(entityManager.find(Familia.class, discussion.getFamiliaId()).getLeader_id());
        }
        return destinationPersonnage;
    }

    public String displayDestination(Personnage personnage) {
        return personnage.getFirstName() + " " + personnage.getLastName();
    }

    public FormattedDiscussion formatDiscussion (Discussion discussion, Personnage selectedPersonnage) {
        FormattedDiscussion formattedDiscussion = new FormattedDiscussion(
                discussion.getId(),
                discussion.getConversationType(),
                this.displayDestination(this.getDestinationPersonnage(discussion, selectedPersonnage)),
                this.getDestinationPersonnage(discussion, selectedPersonnage).getImage(),
                messageService.formatDate(this.getLastMessageDate(discussion))
        );
        return formattedDiscussion;
    }

    public List<FormattedDiscussion> getformattedDiscussions (Personnage selectedPersonnage) {
        List<Discussion> discussions = this.getDiscussionsByPersonnage(selectedPersonnage.getId());
        List<FormattedDiscussion> formattedDiscussions = new ArrayList();
        for (Discussion discussion : discussions) {
            formattedDiscussions.add(this.formatDiscussion(discussion, selectedPersonnage));
        }
        return formattedDiscussions;
    }

    public Date getLastMessageDate(Discussion discussion) {
        List<Message> messages = messageService.getMessagesByDiscussion(discussion.getId());
        Date maxDate = messages.get(0).getDate();
        for (Message message : messages) {
            if (message.getDate().after(maxDate)) {
                maxDate = message.getDate();
            }
        }
        return maxDate;
    }
}