package com.isep.appli.repositories;
import com.isep.appli.models.Player;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	public List<Player> findByEmail(String name);

}
