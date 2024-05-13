package com.isep.appli.controllers;

import com.isep.appli.dbModels.Message;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
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

import java.io.ByteArrayInputStream;
import java.util.Base64;
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
		checkIsUser(user, model);

		List<Personnage> personnages = personnageService.getPersonasByUser(user);
		if(!personnages.isEmpty()){
			session.setAttribute("personnage", personnages.get(0));
		}

		model.addAttribute("user", user);
		model.addAttribute("personnages", personnages);
		model.addAttribute("newPersonnage", new Personnage());
		return "user-profile";
	}

	@PostMapping("/save-personnage")
	public String savePersonaToUser(@Valid Personnage personnage,
									@RequestParam("croppedImageData") String croppedImageData,
									HttpSession session,
									Model model) {
		User user = (User) session.getAttribute("user");

		String base64Image = croppedImageData.split(",")[1]; // Extract Base64 portion
		byte[] decodedImageData = Base64.getDecoder().decode(base64Image.getBytes());

		ByteArrayInputStream bis = new ByteArrayInputStream(decodedImageData);

		if (personnageService.savePersona(bis, user, personnage)) {
			return "redirect:/user-profile";
		}

		model.addAttribute("createPersonaError", true);
		return "user-profile";
	}


	@GetMapping("/personnage/{id}")
	public ResponseEntity<PersonnageDto> getPersonnageById(@PathVariable long id, HttpSession session) {
		Personnage currentPersonnage = personnageService.getPersonnageById(id);

		session.setAttribute("personnage", currentPersonnage);

		PersonnageDto personnageDto = new PersonnageDto(
				currentPersonnage.getId(),
				currentPersonnage.getFirstName(),
				currentPersonnage.getLastName(),
				currentPersonnage.getImage(),
				currentPersonnage.getLevel(),
				currentPersonnage.getMoney(),
				currentPersonnage.getUser().getId(),
				currentPersonnage.getRace().getDisplayName()
		);
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
