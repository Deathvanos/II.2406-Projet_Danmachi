package com.isep.appli.services;

import java.util.HashMap;
import java.util.Map;

import com.isep.appli.models.ModifyUserInfoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isep.appli.dbModels.User;
import com.isep.appli.repositories.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(false);
		user.setIsAdmin(false);
		userRepository.save(user);

		return "signup";
	}

	public Map<String, Boolean> checkUnique(Map<String, String> userInfo) {
		Boolean existingEmail = false;
		Boolean existingUsername = false;
		if (!userInfo.get("email").isEmpty()) {
			existingEmail = userRepository.findUserByEmail(userInfo.get("email")) != null;
		}
		if (!userInfo.get("email").isEmpty()) {
			existingUsername = userRepository.findUserByUsername(userInfo.get("username")) != null;
		}

		Map<String, Boolean> uniqueMap = new HashMap<>();
		uniqueMap.put("existingEmail", existingEmail);
		uniqueMap.put("existingUsername", existingUsername);

		return uniqueMap;
	}

	public boolean confirmEmail(long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setEnabled(true);
			userRepository.save(user);
			return true;
		} else {
			return false;
		}
	}

	public User login(String email, String password) {
		User user = userRepository.findUserByEmail(email);
		if (user != null && user.getEnabled() == true) {
			String hashedPassword = user.getPassword();
			if (passwordEncoder.matches(password, hashedPassword) || password == hashedPassword) {
				return user;
			}
		}
		return null;
	}

	public void modifyUserInfo(User user, ModifyUserInfoForm newUserInfo) {
		if (!newUserInfo.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(newUserInfo.getPassword()));
		}

		if (!newUserInfo.getUsername().isEmpty()) {
			user.setUsername(newUserInfo.getUsername());
		}

		if (!newUserInfo.getEmail().isEmpty()) {
			user.setEmail(newUserInfo.getEmail());
			user.setEnabled(false);
		}

		userRepository.save(user);
	}

	public Page<User> getAllUsers(Pageable pageable) {
		return this.userRepository.findAll(pageable);
	}

	public User getUserById(long userId) {
		return userRepository.findById(userId).orElse(null);
	}


}
