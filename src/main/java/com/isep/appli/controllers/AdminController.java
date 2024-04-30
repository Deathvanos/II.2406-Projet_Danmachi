package com.isep.appli.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {




    @GetMapping("/admin")
    public String subscriptionPage(Model model, HttpSession session) {
        /* Gestion Security Session - je suppose que c'est :
        *  user = session.getuser()
        * if user.isLogin() and user.isAdmin:
        *   acces à la page
        * else :
        *   access refusé
        * */
        return "mainAdmin";
    }


}
