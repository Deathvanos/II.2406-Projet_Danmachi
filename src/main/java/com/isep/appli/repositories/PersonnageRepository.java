package com.isep.appli.repositories;

import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnageRepository extends JpaRepository<Personnage, Long> {
    List<Personnage> findPersonasByUser(User user);

    Personnage findPersonnageById(long id);
}
