package com.isep.appli.services;

import com.isep.appli.dbModels.JoinRequest;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.repositories.JoinRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;

    private final FamiliaService familiaService;

    JoinRequestService(JoinRequestRepository joinRequestRepository, FamiliaService familiaService) {
        this.joinRequestRepository = joinRequestRepository;
        this.familiaService = familiaService;
    }

    // Crée une requête pour rejoindre une familia pour un personnage
    public void createJoinRequest(Personnage personnage, Familia familia) {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPersonnage(personnage);
        joinRequest.setFamilia(familia);
        joinRequestRepository.save(joinRequest);
    }

    // Récupère toutes les demandes en attente pour une familia spécifique
    public List<JoinRequest> getPendingRequestsByFamilia(Familia familia) {
        return joinRequestRepository.findByFamiliaAndAcceptedFalse(familia);
    }

    // Récupère toutes les demandes en attente pour un personnage spécifique
    public List<JoinRequest> getPendingRequestsByPersonnage(Personnage personnage) {
        return joinRequestRepository.findByPersonnageAndAcceptedIsNull(personnage);
    }

    // Accepte une demande de rejoindre une familia
    public void acceptJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        joinRequest.setAccepted(true);
        joinRequestRepository.save(joinRequest);

        Personnage personnage = joinRequest.getPersonnage();
        familiaService.joinFamilia(personnage, joinRequest.getFamilia());
    }

    // Rejette une demande de rejoindre une familia
    public void rejectJoinRequest(Long requestId) {
        joinRequestRepository.deleteById(requestId);
    }

    // Récupère une demande de rejoindre une familia par son ID
    public JoinRequest getJoinRequestById(Long requestId) {
        return joinRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
    }

    // Supprime une demande de rejoindre une familia
    public void deleteJoinRequest(JoinRequest joinRequest) {
        joinRequestRepository.delete(joinRequest);
    }
}
