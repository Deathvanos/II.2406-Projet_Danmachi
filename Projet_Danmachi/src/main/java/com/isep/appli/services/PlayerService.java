package com.isep.appli.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isep.appli.models.Player;
import com.isep.appli.repositories.PlayerRepository;


@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	
	public Iterable<Player> getAll() {
		return playerRepository.findAll();
	}
	
	public Optional<Player> getById(Long id) {
		return playerRepository.findById(id);
	}
	
	public Player save(Player player) {
		if (player.getId() == null) {
			player.setCreatedAt(Instant.now());
		}
		player.setUpdatedAt(Instant.now());
		return playerRepository.save(player);
	}
	
	public void delete(Player player) {
		playerRepository.delete(player);
	}
	
	public List<Player> findByEmail(String email) {
		return this.playerRepository.findByEmail(email);
	}
}
