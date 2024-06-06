package com.isep.appli.repositories;

import com.isep.appli.dbModels.JoinRequest;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByFamiliaAndAcceptedFalse(Familia familia);
    List<JoinRequest> findByFamilia(Familia familia);
    List<JoinRequest> findByPersonnageAndAcceptedIsNull(Personnage personnage);
}
