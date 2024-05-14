package com.isep.appli.controllers;

import com.isep.appli.dbModels.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/home", "/"})
    public String homePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        return "index";
    }

    @GetMapping("/adventurer")
    public String adventurerPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/adventurer";
    }

    @GetMapping("/divinity")
    public String divinityPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/divinity";
    }

    @GetMapping("/races")
    public String racesPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/races";
    }

    @GetMapping("/metiers")
    public String metiersPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/metiers";
    }

    @GetMapping("/familias")
    public String familiasPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/familias";
    }

    @GetMapping("/health")
    public String healthPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/health";
    }

    @GetMapping("/excelia")
    public String exceliaPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/excelia";
    }

    @GetMapping("/reputation")
    public String reputationPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/reputation";
    }
    @GetMapping("/fight")
    public String fightPage(Model model, HttpSession session) {
        // Check user session
        User user = (User) session.getAttribute("user");
        if (UserController.checkIsUser(user, model).equals("200")){model.addAttribute("user", user);}
        // return the page
        return "guest/fight";
    }


}
