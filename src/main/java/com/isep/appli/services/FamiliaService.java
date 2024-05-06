package com.isep.appli.services;
import com.isep.appli.models.Familia;
import com.isep.appli.models.User;
import com.isep.appli.repositories.FamiliaRepository;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class FamiliaService {
    private final ImageService imageService;
    @Autowired
    private FamiliaRepository familiaRepository;

    public Iterable<Familia> getAll() {
        return familiaRepository.findAll();
    }

    FamiliaService(FamiliaRepository familiaRepository, ImageService imageService) {
        this.familiaRepository = familiaRepository;
        this.imageService = imageService;
    }

    public Familia findFamiliaById(Long id) {
        return this.familiaRepository.findFamiliaById(id);
    }

    public boolean createFamilia(MultipartFile file, User user, Familia familia) {
        familia.setLeader_id(user.getId());
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")) {
            return false;
        }
        try {
            byte[] compressedFile = imageService.compressImage(file);
            familia.setEmbleme_image(Base64.getEncoder().encodeToString(compressedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        familiaRepository.save(familia);
        return true;
    }

    //Modifier le personnage pour ajouter l'id de sa nouvelle maison

}