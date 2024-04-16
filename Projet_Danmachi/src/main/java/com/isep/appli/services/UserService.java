package com.isep.appli.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isep.appli.models.User;
import com.isep.appli.repositories.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> getAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
}
