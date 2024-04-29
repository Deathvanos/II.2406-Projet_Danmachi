package com.isep.appli.controllers;

import com.isep.appli.models.Familia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FamiliaPageController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/familiaPage/{familiaId}")
    public String familiaPage(@PathVariable Long familiaId, Model model) {
        // Utilisation de l'EntityManager pour récupérer la Familia par son ID
        Familia familia = entityManager.find(Familia.class, familiaId);
        if (familia == null) {
            throw new IllegalArgumentException("Cette familia n'existe pas.");
        }

        // Ajout de la Familia au modèle pour l'affichage dans la page Thymeleaf
        model.addAttribute("familia", familia);

        return "familiaPage";
    }

    @GetMapping("/new_familia")
    public String familiaPage() {
       return "newFamilia";
    }
}
