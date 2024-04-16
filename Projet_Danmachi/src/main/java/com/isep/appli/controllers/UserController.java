package com.isep.appli.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.isep.appli.models.User;
import com.isep.appli.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	


	
	/*******************************************************************************/
	/******************************** ALL ******************************************/
	/*******************************************************************************/
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("users", userService.getAll());
		return modelAndView;
	}
	
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}

	/*******************************************************************************/
	/******************************** GUEST ****************************************/
	/*******************************************************************************/
	
	
	@GetMapping("/subscription") 
	public String subscriptionPage(Model model) {
		model.addAttribute("user", new User());
		return "subscription";
	}
	
	@PostMapping("/subscription") 
		public String checkSubscription(@Valid User user, BindingResult result, Model model) {
		// Check if the mail is available
		String email = user.getEmail();
		User u = userService.findByEmail(email);
		// If not then error
		if (u != null) {
			System.out.println("Mail already taken");
			return "redirect:/subscription";
		}
		// Else create the player
		userService.save(user);
		return "redirect:/login";
	}
	
	@GetMapping("/login") 
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login") 
	public String checkLogin(@Valid User user, BindingResult result, Model model) {
		
		String email = user.getEmail();
		User u = userService.findByEmail(email);
		if (u != null) {
			System.out.println("Mail inexistant");
			return "redirect:/login";
		}
		return "redirect:/homePlayer";
	}
	
	
	/*******************************************************************************/
	/******************************** PLAYER ***************************************/
	/*******************************************************************************/

	@GetMapping("/homePlayer") 
	public String checkLogin() {
		return "homePlayer";
	}
}
