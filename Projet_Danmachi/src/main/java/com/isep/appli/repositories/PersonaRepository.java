package com.isep.appli.repositories;

import com.isep.appli.models.Persona;
import com.isep.appli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findPersonasByUser(User user);
}
