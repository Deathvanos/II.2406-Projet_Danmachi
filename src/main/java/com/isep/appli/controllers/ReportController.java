package com.isep.appli.controllers;

import com.isep.appli.dbModels.*;
import com.isep.appli.services.PersonnageService;
import com.isep.appli.services.ReportService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;

@Controller
public class ReportController {
    private final PersonnageService personnageService;
    private final ReportService reportService;

    ReportController(PersonnageService personnageService, ReportService reportService) {
        this.personnageService= personnageService;
        this.reportService = reportService;
    }

    @GetMapping("/reportPage/{typeObjectToReport}/{objectId}")
    public String reportPage(@PathVariable String typeObjectToReport, @PathVariable Long objectId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);

        model.addAttribute("objectToReport", reportService.getObjectToReport(typeObjectToReport, objectId));

        Report report = new Report();
        report.setUserFromId(user.getId());
        report.setObjectReportedType(typeObjectToReport);
        report.setObjectReportedId(objectId);
        model.addAttribute("report", report);

        return "reportPage";
    }

    @GetMapping("/reportSuccessPage")
    public String reportSuccessPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("formerPageLink", '#');
        return "reportSuccessPage";
    }

    @PostMapping("sendReport")
    public String sendMessage(@Valid Report report, Model model) {
        report.setDate(new Date());
        reportService.save(report);
        return "redirect:/reportSuccessPage";
    }
}