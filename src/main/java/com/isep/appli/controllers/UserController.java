package com.isep.appli.controllers;

import com.isep.appli.models.*;
import com.isep.appli.services.EmailService;
import com.isep.appli.services.PersonnageService;
import com.isep.appli.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
	@Autowired
	private PersonnageService personnageService;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService emailService;


	static public String checkIsUser(User user, Model model) {
		if (user==null) {return "errors/error-401";}
		model.addAttribute("user", user);
		return "200";
	}

	@GetMapping("/user-profile")
	public String checkLogin(Model model, HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}

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
	public ResponseEntity<PersonnageDto> getPersonnageById(@PathVariable long id) {
		Personnage currentPersonnage = personnageService.getPersonnageById(id);

		PersonnageDto personnageDto = new PersonnageDto(currentPersonnage, currentPersonnage.getRace().getDisplayName());
		return ResponseEntity.ok(personnageDto);
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

	@PostMapping("/modify-user")
	public String updateUser(@ModelAttribute ModifyUserInfoForm newUserInfo, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<String, String> checkUniqueParameters = new HashMap<>();

		if (!newUserInfo.getUsername().isEmpty()) {
			checkUniqueParameters.put("username", newUserInfo.getUsername());
		}
		if (!newUserInfo.getEmail().isEmpty()) {
			checkUniqueParameters.put("email", newUserInfo.getEmail());
		}

		userService.modifyUserInfo(user, newUserInfo);

		if (newUserInfo.getEmail() != null && !newUserInfo.getEmail().isEmpty()) {
			emailService.sendConfirmationEmail(user);
		}

		return "redirect:/login";
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
