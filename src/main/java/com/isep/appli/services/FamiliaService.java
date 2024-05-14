package com.isep.appli.services;
import com.isep.appli.dbModels.Familia;
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

@Service
public class FamiliaService {
    @Autowired
    private ImageService imageService;
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

    public boolean createFamilia(byte[] compressedImage, User user, Familia familia) {
        familia.setLeader_id(user.getId());
        familia.setEmbleme_image(Base64.getEncoder().encodeToString(compressedImage));
        familiaRepository.save(familia);
        return true;
    }


}