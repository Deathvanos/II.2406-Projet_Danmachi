package com.isep.appli.controllers;

import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.models.PersonnageDto;
import com.isep.appli.models.enums.Race;
import com.isep.appli.services.PersonnageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class PersonnageController {
    @Autowired
    private PersonnageService personnageService;

    @PostMapping("/save-personnage")
    public String savePersonnageToUser(@Valid Personnage personnage,
                                       @RequestParam("croppedImageData") String croppedImageData,
                                       HttpSession session,
                                       Model model) {
        User user = (User) session.getAttribute("user");

        String base64Image = croppedImageData.split(",")[1]; // Extract Base64 portion
        byte[] decodedImageData = Base64.getDecoder().decode(base64Image.getBytes());

        ByteArrayInputStream bis = new ByteArrayInputStream(decodedImageData);

        if (personnageService.savePersonnage(bis, user, personnage)) {
            return "redirect:/user-profile";
        }

        model.addAttribute("createPersonaError", true);
        return "user-profile";
    }

    @GetMapping("/session/personnage")
    public ResponseEntity<Personnage> getSessionPersonnage(HttpSession session) {
        return ResponseEntity.ok((Personnage)session.getAttribute("personnage"));
    }

    @PostMapping("/personnage/description")
    public String setPersonnageDescription(@RequestParam String description, HttpSession session) {
        Personnage personnage = (Personnage) session.getAttribute("personnage");
        personnageService.saveDescriptionPersonnage(personnage, description);
        return "redirect:/user-profile";
    }

    @PostMapping("/personnage/story")
    public String setPersonnageStory(@RequestParam String story, HttpSession session) {
        Personnage personnage = (Personnage) session.getAttribute("personnage");
        personnageService.saveStoryPersonnage(personnage, story);
        return "redirect:/user-profile";
    }

    @DeleteMapping("/personnage/{id}")
    public ResponseEntity<String> deletePersonnageById(@PathVariable long id) {
        try {
            personnageService.deletePersonnageById(id);
            return ResponseEntity.ok("Personnage bien suprimer");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting personnage");
        }
    }

    @GetMapping("/personnage/{id}")
    public ResponseEntity<PersonnageDto> getPersonnageById(@PathVariable long id, HttpSession session) {
        Personnage currentPersonnage = personnageService.getPersonnageById(id);

        session.setAttribute("personnage", currentPersonnage);

        PersonnageDto personnageDto = new PersonnageDto(
                currentPersonnage.getId(),
                currentPersonnage.getFirstName(),
                currentPersonnage.getLastName(),
                currentPersonnage.getImage(),
                currentPersonnage.getLevel(),
                currentPersonnage.getMoney(),
                currentPersonnage.getUser().getId(),
                currentPersonnage.getRace().getDisplayName(),
                currentPersonnage.getDescription(),
                currentPersonnage.getStory(),
                currentPersonnage.getUser().getUsername()
        );
        return ResponseEntity.ok(personnageDto);
    }

    @GetMapping("/personnage/all")
    public String getAllPersonnagesToTable(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        if(session.getAttribute("user") == null){
            return "errors/error-401";
        }

        Pageable pageable = PageRequest.of(page, 5);
        Page<PersonnageDto> personnagePage = personnageService.getAllPersonnages(pageable);

        model.addAttribute("personnages", personnagePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", personnagePage.getTotalPages());

        List<String> distinctRaces = new ArrayList<>();
        for (Race race : Race.values()) {
            distinctRaces.add(race.getDisplayName());
        }
        model.addAttribute("distinctRaces", distinctRaces);

        return "/personnagesTable";
    }
}
