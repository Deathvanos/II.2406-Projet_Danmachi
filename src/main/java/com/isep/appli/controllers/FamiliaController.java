package com.isep.appli.controllers;

import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.dbModels.JoinRequest;
import com.isep.appli.models.enums.Race;
import com.isep.appli.services.FamiliaService;
import com.isep.appli.services.ImageService;
import com.isep.appli.services.PersonnageService;
import com.isep.appli.services.JoinRequestService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.isep.appli.controllers.UserController.checkIsUser;

@Controller
@RequestMapping("familia")
public class FamiliaController {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ImageService imageService;
    @Autowired
    private final FamiliaService familiaService;
    @Autowired
    private final PersonnageService personnageService;
    @Autowired
    private PersonnageController personnageController;
    @Autowired
    private JoinRequestService joinRequestService;
    public FamiliaController(PersonnageService personnageService, FamiliaService familiaService, JoinRequestService joinRequestService) {
        this.familiaService = familiaService;
        this.personnageService = personnageService;
        this.joinRequestService = joinRequestService;
    }

    @GetMapping("/{familiaId}")
    public String familiaPage(@PathVariable Long familiaId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}

        // Utilisation de l'EntityManager pour récupérer la Familia par son ID
        Familia familia = entityManager.find(Familia.class, familiaId);
        if (familia == null) {
            throw new IllegalArgumentException("Cette familia n'existe pas.");
        }

        Personnage leader = personnageService.getPersonnageById(familia.getLeader_id());
        if (leader == null) {
            throw new IllegalArgumentException("Le nom du leader n'est pas correct.");
        }

        List<Personnage> members = personnageService.getPersonnagesByFamiliaId(familiaId);

        List<String> distinctRaces = new ArrayList<>();
        for (Race race : Race.values()) {
            distinctRaces.add(race.getDisplayName());
        }
        model.addAttribute("distinctRaces", distinctRaces);


        // Filtrer la liste des membres pour enlever le leader
        List<Personnage> filteredMembers = members.stream()
                .filter(member -> !member.getId().equals(leader.getId()))
                .collect(Collectors.toList());

        List<JoinRequest> pendingRequests = joinRequestService.getPendingRequestsByFamilia(familia);

        model.addAttribute("familia", familia);
        model.addAttribute("leader", leader);
        model.addAttribute("leaderFirstName", leader.getFirstName());
        model.addAttribute("leaderLastName", leader.getLastName());
        model.addAttribute("leaderImage", leader.getImage());
        model.addAttribute("members", filteredMembers);
        model.addAttribute("familiaId", familia.getId());
        model.addAttribute("pendingRequests", pendingRequests);

        // Vérification si le personnage a déjà une Familia
        Personnage personnage = personnageController.getSessionPersonnage(session).getBody();
        if (personnage != null && personnage.getFamilia() == null && personnage.getRace() != Race.GOD) {
            boolean hasPendingRequest = pendingRequests.stream()
                    .anyMatch(request -> request.getPersonnage().getId().equals(personnage.getId()));
            model.addAttribute("canJoin", !hasPendingRequest);
            model.addAttribute("hasPendingRequest", hasPendingRequest);
        } else {
            model.addAttribute("canJoin", false);
            model.addAttribute("hasPendingRequest", false);
        }

        boolean isLeader = personnage != null && personnage.getId().equals(leader.getId());
        model.addAttribute("isLeader", isLeader);

        return "familiaPage";
    }
    @GetMapping("/new")
    public String createFamiliaPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")){return checkUser;}

        model.addAttribute("familia", new Familia());
        return "newFamilia";
    }

    @PostMapping("/new")
    public String createFamilia(@Valid Familia familia, @RequestParam("croppedImageData") String croppedImageData, BindingResult result, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")) { return checkUser; }
        Personnage personnage = personnageController.getSessionPersonnage(session).getBody();
        if (personnage != null) {
            byte[] decodedImageData = Base64.getDecoder().decode(croppedImageData);
            try {
                byte[] compressedImageData = imageService.compressImage(new ByteArrayInputStream(decodedImageData));
                if (familiaService.createFamilia(compressedImageData, personnage, familia)) {
                    return "redirect:/home"; //Rediriger vers la page de la familia qui vient d'être créée
                }
            } catch (IOException e) {
                // Gérer l'erreur d'une manière appropriée, par exemple, en renvoyant une page d'erreur
                e.printStackTrace();
            }
        }
        return "home";
        //TODO cas où il n'y a pas de perso dans la session
    }


    @GetMapping("/list")
    public String familiaList(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}

        Map<Familia, Personnage> familiasWithLeaders = familiaService.getAllFamiliasWithLeaders();
        model.addAttribute("familiasWithLeaders", familiasWithLeaders);

        Personnage personnage = personnageController.getSessionPersonnage(session).getBody();
        if (personnage != null && personnage.getFamilia() == null && personnage.getRace()==Race.GOD) {
            model.addAttribute("canCreate", true);
        } else {
            model.addAttribute("canCreate", false);
        }
        System.out.println(model.getAttribute("canCreate"));

        return "familiaList";
    }

    @GetMapping("/redirect")
    public String redirectBasedOnFamilia(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")) { return checkUser; }

        Personnage personnage = personnageController.getSessionPersonnage(session).getBody();
        if (personnage != null && personnage.getFamilia() != null) {
            return "redirect:/familia/" + personnage.getFamilia().getId();
        } else {
            return "redirect:/familia/list";
        }

    }

    @PostMapping("/request/{familiaId}")
    public String requestJoinFamilia(@PathVariable Long familiaId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        String checkUser = checkIsUser(user, model);
        if (!checkUser.equals("200")) { return checkUser; }

        Familia familia = entityManager.find(Familia.class, familiaId);
        if (familia == null) {
            throw new IllegalArgumentException("Cette familia n'existe pas.");
        }

        Personnage personnage = personnageController.getSessionPersonnage(session).getBody();
        if (personnage != null && personnage.getFamilia() == null) {
            joinRequestService.createJoinRequest(personnage, familia);
            return "redirect:/familia/" + familiaId;
        }
        return "home";
    }

    @PostMapping("/requests/accept/{requestId}")
    public String acceptJoinRequest(@PathVariable Long requestId) {
        joinRequestService.acceptJoinRequest(requestId);
        JoinRequest joinRequest = joinRequestService.getJoinRequestById(requestId);
        return "redirect:/familia/" + joinRequest.getFamilia().getId();
    }

    @PostMapping("/requests/reject/{requestId}")
    public String rejectJoinRequest(@PathVariable Long requestId) {
        JoinRequest joinRequest = joinRequestService.getJoinRequestById(requestId);
        joinRequestService.rejectJoinRequest(requestId);
        return "redirect:/familia/" + joinRequest.getFamilia().getId();
    }

    @PostMapping("/removeMember/{familiaId}/{memberId}")
    public String removeMember(@PathVariable Long familiaId, @PathVariable Long memberId) {
        familiaService.removeMember(familiaId, memberId);
        return "redirect:/familia/" + familiaId;
    }
}