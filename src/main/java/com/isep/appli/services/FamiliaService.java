package com.isep.appli.services;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.repositories.FamiliaRepository;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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

    public boolean createFamilia(byte[] compressedImage, User user, Familia familia) {
        familia.setLeader_id(user.getId());
        familia.setEmbleme_image(Base64.getEncoder().encodeToString(compressedImage));
        familiaRepository.save(familia);
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

}