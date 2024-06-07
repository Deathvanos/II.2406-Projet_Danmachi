package com.isep.appli.repositories;

import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.models.enums.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    List<Personnage> findPersonasByUser(User user);

    Personnage findPersonnageById(long id);

    Page<Personnage> findByRace(Race race, Pageable pageable);

    List<Personnage> findAllByUserIn(List<User> usersList);
}
