package com.isep.appli.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.isep.appli.models.Player;
import com.isep.appli.services.PlayerService;

import jakarta.validation.Valid;

@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	


	
	/*******************************************************************************/
	/******************************** ALL ******************************************/
	/*******************************************************************************/
	@GetMapping("/home")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("users", playerService.getAll());
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
		model.addAttribute("player", new Player());
		return "subscription";
	}
	
	@PostMapping("/subscription") 
		public String checkSubscription(@Valid Player player, BindingResult result, Model model) {
		// Check if the mail is available
		String email = player.getEmail();
		List<Player> p = playerService.findByEmail(email);
		// If not then error
		if (!p.isEmpty()) {
			System.out.println("Mail already taken");
			return "redirect:/subscription";
		}
		// Else create the player
		player.setRole("Player");
		playerService.save(player);
		return "redirect:/login";
	}
	
	@GetMapping("/login") 
	public String loginPage(Model model) {
		model.addAttribute("player", new Player());
		return "login";
	}
	
	@PostMapping("/login") 
	public String checkLogin(@Valid Player player, BindingResult result, Model model) {
		
		String email = player.getEmail();
		List<Player> p = playerService.findByEmail(email);
		if (p.isEmpty()) {
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
