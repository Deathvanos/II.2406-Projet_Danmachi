package com.isep.appli.controllers;

import com.isep.appli.models.Message;
import com.isep.appli.models.Personnage;
import com.isep.appli.models.User;
import com.isep.appli.services.PersonnageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class UserController {
	private final PersonnageService personnageService;

	UserController(PersonnageService personnageService) {
		this.personnageService = personnageService;
	}

	@GetMapping("/user-profile")
	public String checkLogin(Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		List<Personnage> personnages = personnageService.getPersonasByUser(user);

		model.addAttribute("user", user);
		model.addAttribute("personnages", personnages);
		model.addAttribute("newPersonnage", new Personnage());
		return "user-profile";
	}

	@PostMapping("/save-personnage")
	public String savePersonaToUser(@Valid Personnage personnage,
									@RequestParam("file") MultipartFile file,
									HttpSession session,
									Model model)
	{
		User user = (User) session.getAttribute("user");

		if (personnageService.savePersona(file, user, personnage)) {
			return "redirect:/user-profile";
		}

		model.addAttribute("createPersonaError", true);
		return "user-profile";
	}

	@GetMapping("/personnage/{id}")
	public ResponseEntity<Personnage> getPersonnageById(@PathVariable long id) {
		Personnage currentPersonnage = personnageService.getPersonnageById(id);
		return ResponseEntity.ok(currentPersonnage);
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
	
	@GetMapping("/chatPage")
	public String chatPage() {
		return "chatPage";
	}

	@GetMapping("/sendMessage")
	public String sendMessage(@Valid Message message) {

		return "chatPage";
	}
}
