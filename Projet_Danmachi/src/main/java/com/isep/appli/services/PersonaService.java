package com.isep.appli.services;

import com.isep.appli.models.Personnage;
import com.isep.appli.models.User;
import com.isep.appli.repositories.PersonaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;
    private final int baseLevel = 1;
    private final int baseMoney = 15000;

    PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Personnage> getPersonasByUser(User user) {
        return personaRepository.findPersonasByUser(user);
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
            personnage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        personaRepository.save(personnage);
        return true;
    }
}
