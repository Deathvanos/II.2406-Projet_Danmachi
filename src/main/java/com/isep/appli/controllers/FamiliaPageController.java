package com.isep.appli.controllers;

import com.isep.appli.models.Familia;
import com.isep.appli.models.Personnage;
import com.isep.appli.models.User;
import com.isep.appli.services.FamiliaService;
import com.isep.appli.services.PersonnageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static com.isep.appli.controllers.AdminController.checkIsAdmin;
import static com.isep.appli.controllers.UserController.checkIsUser;

@Controller
public class FamiliaPageController {

    @PersistenceContext
    private EntityManager entityManager;

    private final FamiliaService familiaService;
    private final PersonnageService personnageService;
    public FamiliaPageController(PersonnageService personnageService,FamiliaService familiaService) {
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
        //model.addAttribute("leaderFirstName", leader.getFirstName());
        //model.addAttribute("leaderLastName", leader.getLastName());
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
    public String createFamilia(@Valid Familia familia,@RequestParam("file") MultipartFile file, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")){return checkUser;}

        //Récupérer le perso         Personnage currentPersonnage = (Personnage) session.getAttribute("personnage");
        //model.addAttribute("personnage", );
        //Comme ça on demandera que le personnage n'ait pas de familia pour en créer une

        if (familiaService.createFamilia(file, user, familia)) {
            return "redirect:/home"; //Rediriger vers la page de la familia qui vient d'être créée
        }
        return "home";
    }




}