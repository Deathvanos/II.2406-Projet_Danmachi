package com.isep.appli.services;

import com.isep.appli.dbModels.JoinRequest;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Familia;
import com.isep.appli.repositories.JoinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinRequestService {

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private FamiliaService familiaService;

    JoinRequestService(JoinRequestRepository joinRequestRepository, FamiliaService familiaService) {
        this.joinRequestRepository = joinRequestRepository;
        this.familiaService = familiaService;
    }

    public void createJoinRequest(Personnage personnage, Familia familia) {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setPersonnage(personnage);
        joinRequest.setFamilia(familia);
        joinRequestRepository.save(joinRequest);
    }

    public List<JoinRequest> getPendingRequestsByFamilia(Familia familia) {
        return joinRequestRepository.findByFamiliaAndAcceptedFalse(familia);
    }
    public List<JoinRequest> getPendingRequestsByPersonnage(Personnage personnage) {
        return joinRequestRepository.findByPersonnageAndAcceptedIsNull(personnage);
    }

    public void acceptJoinRequest(Long requestId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
        joinRequest.setAccepted(true);
        joinRequestRepository.save(joinRequest);

        Personnage personnage = joinRequest.getPersonnage();
        familiaService.joinFamilia(personnage, joinRequest.getFamilia());
    }

    public void rejectJoinRequest(Long requestId) {
        joinRequestRepository.deleteById(requestId);
    }

    public JoinRequest getJoinRequestById(Long requestId) {
        return joinRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("Invalid request ID"));
    }
    public void deleteJoinRequest(JoinRequest joinRequest) {
        joinRequestRepository.delete(joinRequest);
    }
}
