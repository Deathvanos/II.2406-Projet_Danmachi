package com.isep.appli.services;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;

import com.isep.appli.repositories.FamiliaRepository;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FamiliaService {
    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private PersonnageRepository personnageRepository;

    FamiliaService(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
        this.personnageRepository = personnageRepository;
    }

    public Iterable<Familia> getAllFamilias() {
        return familiaRepository.findAll();
    }

    public Familia findFamiliaById(Long id) {
        return this.familiaRepository.findFamiliaById(id);
    }

    public boolean createFamilia(byte[] compressedImage, Personnage personnage, Familia familia) {
        familia.setLeader_id(personnage.getId());
        familia.setEmbleme_image(Base64.getEncoder().encodeToString(compressedImage));
        familiaRepository.save(familia);

        personnage.setFamilia(familia);
        personnageRepository.save(personnage);
        return true;
    }
    public Map<Familia, Personnage> getAllFamiliasWithLeaders() {
        Iterable<Familia> familias = getAllFamilias();
        Map<Familia, Personnage> familiaWithLeaders = new HashMap<>();
        for (Familia familia : familias) {
            Personnage leader = personnageRepository.findPersonnageById(familia.getLeader_id());
            familiaWithLeaders.put(familia, leader);
        }
        return familiaWithLeaders;
    }

    public boolean joinFamilia(Personnage personnage, Familia familia){
        personnage.setFamilia(familia);
        personnageRepository.save(personnage);
        return true;
    }

    public void removeMember(Long familiaId, Long memberId) {
        Familia familia = familiaRepository.findById(familiaId).orElse(null);
        if (familia != null) {
            List<Personnage> members = personnageRepository.findByFamiliaId(familiaId);
            Personnage memberToRemove = members.stream()
                    .filter(member -> member.getId().equals(memberId))
                    .findFirst()
                    .orElse(null);
            if (memberToRemove != null) {
                memberToRemove.setFamilia(null); // Mettre à null le ID de famille du membre
                personnageRepository.save(memberToRemove); // Enregistrer les modifications du personnage
            } else {
                throw new IllegalArgumentException("Le personnage spécifié n'est pas membre de cette famille.");
            }
        } else {
            throw new IllegalArgumentException("La famille spécifiée n'existe pas.");
        }
    }

}