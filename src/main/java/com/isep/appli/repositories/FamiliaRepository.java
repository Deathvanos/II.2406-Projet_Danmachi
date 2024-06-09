package com.isep.appli.repositories;

import com.isep.appli.dbModels.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {
    Familia findFamiliaById(Long id);

    boolean existsById(@NonNull Long id);

}