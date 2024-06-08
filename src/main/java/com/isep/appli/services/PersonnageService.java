package com.isep.appli.services;

import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.JoinRequest;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.User;
import com.isep.appli.models.PersonnageDto;
import com.isep.appli.repositories.PersonnageRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonnageService {
    private final ImageService imageService;
    private final PersonnageRepository personnageRepository;
    private final JoinRequestService joinRequestService;
    private final int baseLevel = 1;
    private final int baseMoney = 15000;

    PersonnageService(PersonnageRepository personnageRepository, ImageService imageService, JoinRequestService joinRequestService) {
        this.personnageRepository = personnageRepository;
        this.imageService = imageService;
        this.joinRequestService = joinRequestService;
    }

    public List<Personnage> getPersonasByUser(User user) {
        return personnageRepository.findPersonasByUser(user);
    }

    public boolean savePersonnage(InputStream imageData, User user, Personnage personnage) {

        personnage.setUser(user);
        personnage.setLevel(baseLevel);
        personnage.setMoney(baseMoney);

        try {
            byte[] compressedImage = imageService.compressImage(imageData);
            personnage.setImage(Base64.getEncoder().encodeToString(compressedImage));
        } catch (IOException e) {
            throw new RuntimeException("Error compressing image", e);
        } finally {
            try {
                imageData.close();
            } catch (IOException e) {
                throw new RuntimeException("Error compressing image", e);
            }
        }

        personnageRepository.save(personnage);
        return true;
    }

    public void updateMoney(Personnage personnage, int money){
        personnage.setMoney(money);
        personnageRepository.save(personnage);
    }

    public Personnage getPersonnageById(long id) {
        return personnageRepository.findPersonnageById(id);
    }

    public List<Personnage> getPersonnagesByFamiliaId(Long familiaId) {
        return personnageRepository.findByFamiliaId(familiaId);
    }

    public void deletePersonnageById(long id) {
        Personnage personnage = personnageRepository.findPersonnageById(id);

        List<JoinRequest> pendingRequests = joinRequestService.getPendingRequestsByPersonnage(personnage);
        for (JoinRequest request : pendingRequests) {
            joinRequestService.deleteJoinRequest(request);
        }

        personnageRepository.delete(personnage);
    }

    public void saveDescriptionPersonnage(Personnage personnage, String description) {
        personnage.setDescription(description);
        personnageRepository.save(personnage);
    }

    public void saveStoryPersonnage(Personnage personnage, String story) {
        personnage.setStory(story);
        personnageRepository.save(personnage);
    }

    public List<PersonnageDto> getAllPersonnages() {
        List<Personnage> personnages = personnageRepository.findAll();

        return personnages.stream()
                .map(personnage -> new PersonnageDto(
                        personnage.getId(),
                        personnage.getFirstName(),
                        personnage.getLastName(),
                        personnage.getImage(),
                        personnage.getLevel(),
                        personnage.getMoney(),
                        personnage.getUser().getId(),
                        personnage.getRace().getDisplayName(),
                        personnage.getDescription(),
                        personnage.getStory(),
                        personnage.getUser().getUsername()
                ))
                .collect(Collectors.toList());
    }


    public List<Personnage> getPersonnagesUsers(List<User> userList) {
        return personnageRepository.findAllByUserIn(userList);
    }

}
