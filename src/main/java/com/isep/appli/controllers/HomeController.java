package com.isep.appli.controllers;

import com.isep.appli.dbModels.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"/home", "/"})
    public String home(Model model, HttpSession session) {

        /*
        * Kill session after x time ?
        * login -> homerUSer X->X login (on va pas sur login page quand on est connecté)
        * */

        User user = (User) session.getAttribute("user");
        String checkUser = UserController.checkIsUser(user, model);
        if (checkUser.equals("200")){model.addAttribute("user", user);}
        return "index";
    }

}
