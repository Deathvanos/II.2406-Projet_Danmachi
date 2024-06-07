package com.isep.appli.services;

import com.isep.appli.dbModels.Familia;
import com.isep.appli.dbModels.Message;
import com.isep.appli.dbModels.Personnage;
import com.isep.appli.dbModels.Report;
import com.isep.appli.repositories.ReportRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final PersonnageService personnageService;
    private final FamiliaService familiaService;
    private final MessageService messageService;

    ReportService(ReportRepository reportRepository, PersonnageService personnageService, FamiliaService familiaService, MessageService messageService) {
        this.reportRepository = reportRepository;
        this.personnageService = personnageService;
        this.familiaService = familiaService;
        this.messageService = messageService;
    }

    public Report getById(Long id) {
        return reportRepository.findReportById(id);
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public void deleteReportById(long id) {
        Report report = getById(id);
        reportRepository.delete(report);
    }

    public List<Report> getAllReportSortedByDate() {
        List<Report> reports = reportRepository.findAll();
        Collections.sort(reports, Comparator.comparing(Report::getDate));
        return reports;
    }

    public String getObjectToReport(String typeObjectToReport, Long objectId) {
        String objectToReport;
        switch (typeObjectToReport) {
            case "PERSONNAGE":
                Personnage personnage = personnageService.getPersonnageById(objectId);
                objectToReport = personnage.getFirstName() + " " + personnage.getLastName();
                break;
            case "FAMILIA":
                Familia familia = familiaService.findFamiliaById(objectId);
                Personnage leader = personnageService.getPersonnageById(familia.getLeader_id());
                objectToReport = "la familia de " + leader.getFirstName() + " " + leader.getLastName();
                break;
            case "MESSAGE":
                Message message = messageService.getById(objectId);
                Personnage sender = personnageService.getPersonnageById(message.getSenderId());
                objectToReport = "le message de " + sender.getFirstName() + " " + sender.getLastName();
                break;
            default:
                objectToReport = "Organisme inconnu";
        }
        return objectToReport;
    }
}