package com.isep.appli.repositories;

import com.isep.appli.models.Familia;
import com.isep.appli.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    Familia findFamiliaById(Long id);
}