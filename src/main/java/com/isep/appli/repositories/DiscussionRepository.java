package com.isep.appli.repositories;

import com.isep.appli.dbModels.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Optional<Discussion> findById(Long id);
    List<Discussion> findByFirstPersonnageId(Long personnage);
    List<Discussion> findBySecondPersonnageId(Long personnage);
    List<Discussion> findByFamiliaId(Long familia);
    List<Discussion> findByFirstPersonnageIdAndSecondPersonnageId(Long firstPersonnageId, Long secondPersonnageId);

}