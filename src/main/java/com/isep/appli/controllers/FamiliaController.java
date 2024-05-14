package com.isep.appli.controllers;

import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.services.FamiliaService;
import com.isep.appli.services.ImageService;
import com.isep.appli.services.PersonnageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import static com.isep.appli.controllers.UserController.checkIsUser;

@Controller
public class FamiliaController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ImageService imageService;
    private final FamiliaService familiaService;
    private final PersonnageService personnageService;
    public FamiliaController(PersonnageService personnageService, FamiliaService familiaService) {
        this.familiaService = familiaService;
        this.personnageService = personnageService;
    }

    @GetMapping("/familiaPage/{familiaId}")
    public String familiaPage(@PathVariable Long familiaId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")){return checkUser;}

        // Utilisation de l'EntityManager pour récupérer la Familia par son ID
        Familia familia = entityManager.find(Familia.class, familiaId);
        if (familia == null) {
            throw new IllegalArgumentException("Cette familia n'existe pas.");
        }

        Personnage leader = personnageService.getPersonnageById(familia.getLeader_id());

        if (leader == null) {
            throw new IllegalArgumentException("Le nom du leader n'est pas correct.");
        }


        model.addAttribute("familia", familia);
        model.addAttribute("leaderFirstName", leader.getFirstName());
        model.addAttribute("leaderLastName", leader.getLastName());
        return "familiaPage";
    }
    @GetMapping("/new_familia")
    public String createFamiliaPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")){return checkUser;}

        model.addAttribute("familia", new Familia());
        return "newFamilia";
    }

    @PostMapping("/new_familia")
    public String createFamilia(@Valid Familia familia, @RequestParam("croppedImageData") String croppedImageData, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")) { return checkUser; }

        byte[] decodedImageData = Base64.getDecoder().decode(croppedImageData);
        try {
            byte[] compressedImageData = imageService.compressImage(new ByteArrayInputStream(decodedImageData));
            if (familiaService.createFamilia(compressedImageData, user, familia)) {
                return "redirect:/home"; //Rediriger vers la page de la familia qui vient d'être créée
            }
        } catch (IOException e) {
            // Gérer l'erreur d'une manière appropriée, par exemple, en renvoyant une page d'erreur
            e.printStackTrace();
        }
        return "home";
    }


    @PostMapping("/familia_list")
    public String familiaList(){
        //A compléter
        return "familiaList";
    }




}