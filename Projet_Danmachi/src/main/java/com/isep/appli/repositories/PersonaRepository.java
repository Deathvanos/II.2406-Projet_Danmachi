package com.isep.appli.repositories;

import com.isep.appli.models.Personnage;
import com.isep.appli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Personnage, Long> {
    List<Personnage> findPersonasByUser(User user);
}
