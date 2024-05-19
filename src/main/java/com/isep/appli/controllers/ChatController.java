package com.isep.appli.controllers;

import com.isep.appli.dbModels.*;
import com.isep.appli.models.FormattedMessage;
import com.isep.appli.services.DiscussionService;
import com.isep.appli.services.MessageService;
import com.isep.appli.services.PersonnageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {
    @PersistenceContext
    private EntityManager entityManager;

    private final MessageService messageService;
    private final DiscussionService discussionService;
    private final PersonnageService personnageService;

    ChatController(MessageService messageService, DiscussionService discussionService, PersonnageService personnageService) {
        this.messageService = messageService;
        this.discussionService = discussionService;
        this.personnageService= personnageService;
    }

    @GetMapping("/chatPage")
    public String chatPageWithoutdiscussionSelected() {
        return "redirect:/chatPage/0";
    }

    @GetMapping("/chatPage/{discussionId}")
    public String chatPage(@PathVariable Long discussionId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Personnage personnage = (Personnage) session.getAttribute("personnage");
        model.addAttribute("personnage", personnage);
        if (personnage != null) {
            model.addAttribute("discussions", discussionService.getformattedDiscussions(personnage));

            Optional<Discussion> discussionOptional = discussionService.getById(discussionId);
            Discussion discussion = discussionOptional.orElse(null);

            if (discussionId != 0) {
                model.addAttribute("discussion", discussionService.formatDiscussion(discussion, personnage));

                Long destination;
                if (discussion.getConversationType().equals("PRIVATE")) {
                    destination = discussionService.getPrivateDestinationId(discussion, personnage);
                }
                else {
                    destination = discussion.getFamiliaId();
                }

                List<FormattedMessage> messages = messageService.getFormattedMessagesByDiscussionId(discussionId, personnage);
                model.addAttribute("messages", messages);

                Message message = new Message();
                message.setDiscussion(discussionId);
                message.setSenderId(personnage.getId());
                message.setDestinationId(destination);
                model.addAttribute("message", message);
            }
        }

        return "chatPage";
    }

    @PostMapping("sendMessage")
    public String sendMessage(@Valid Message message) {
        message.setDate(new Date());

        messageService.save(message);
        return "redirect:/chatPage";
    }
}