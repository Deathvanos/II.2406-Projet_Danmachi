package com.isep.appli.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isep.appli.models.User;
import com.isep.appli.repositories.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String signup(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setEnabled(false);
		user.setIsAdmin(false);
		userRepository.save(user);

		return "signup";
	}

	public boolean checkUnique(String email) {
		return userRepository.findUserByEmail(email) != null;
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
			if (passwordEncoder.matches(password, hashedPassword)) {
				return user;
			}
		}
		return null;
	}
}
