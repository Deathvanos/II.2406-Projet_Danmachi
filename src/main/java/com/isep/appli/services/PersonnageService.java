package com.isep.appli.services;

import com.isep.appli.models.Personnage;
import com.isep.appli.models.User;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class PersonnageService {
    private final ImageService imageService;
    private final PersonnageRepository personnageRepository;
    private final int baseLevel = 1;
    private final int baseMoney = 15000;

    PersonnageService(PersonnageRepository personnageRepository, ImageService imageService) {
        this.personnageRepository = personnageRepository;
        this.imageService = imageService;
    }

    public List<Personnage> getPersonasByUser(User user) {
        return personnageRepository.findPersonasByUser(user);
    }

    public boolean savePersona(MultipartFile file, User user, Personnage personnage) {

        personnage.setUser(user);
        personnage.setLevel(baseLevel);
        personnage.setMoney(baseMoney);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")) {
            return false;
        }
        try {
            byte[] compressedFile = imageService.compressImage(file);
            personnage.setImage(Base64.getEncoder().encodeToString(compressedFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        personnageRepository.save(personnage);
        return true;
    }

    public Personnage getPersonnageById(long id) {
        return personnageRepository.findPersonnageById(id);
    }

    public void deletePersonnageById(long id) {
        Personnage personnage = personnageRepository.findPersonnageById(id);

        personnageRepository.delete(personnage);
    }
}
