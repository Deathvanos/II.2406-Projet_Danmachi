package com.isep.appli.services;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;

import com.isep.appli.repositories.FamiliaRepository;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class FamiliaService {
    @Autowired
    private ImageService imageService;
    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private PersonnageRepository personnageRepository;

    FamiliaService(FamiliaRepository familiaRepository, ImageService imageService) {
        this.familiaRepository = familiaRepository;
        this.imageService = imageService;
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

}