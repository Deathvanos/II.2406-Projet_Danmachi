package com.isep.appli.services;

import com.isep.appli.models.Persona;
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

    PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public List<Persona> getPersonasByUser(User user) {
        return personaRepository.findPersonasByUser(user);
    }

    public boolean savePersona(MultipartFile file, User user, Persona persona) {
        persona.setUser(user);
        persona.setLevel(0);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if(fileName.contains("..")) {
            return false;
        }
        try {
            persona.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        personaRepository.save(persona);
        return true;
    }
}
