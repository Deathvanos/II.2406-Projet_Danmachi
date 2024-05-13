package com.isep.appli.services;

import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
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

    public boolean savePersona(InputStream imageData, User user, Personnage personnage) {

        personnage.setUser(user);
        personnage.setLevel(baseLevel);
        personnage.setMoney(baseMoney);

        try {
            byte[] compressedImage = imageService.compressImage(imageData); // assuming this method compresses the image
            personnage.setImage(Base64.getEncoder().encodeToString(compressedImage));
        } catch (IOException e) {
            throw new RuntimeException("Error compressing image", e);
        } finally {
            try {
                imageData.close(); // close the input stream
            } catch (IOException e) {
                // handle or log any potential exception while closing the stream
            }
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
