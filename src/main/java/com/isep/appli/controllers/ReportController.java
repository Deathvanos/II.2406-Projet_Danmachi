package com.isep.appli.controllers;

import com.isep.appli.dbModels.*;
import com.isep.appli.services.PersonnageService;
import com.isep.appli.services.ReportService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/reportPage/reportSuccessPage")
    public String reportSuccessPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("formerPageLink", "/home");
        return "reportSuccessPage";
    }


    @PostMapping("sendReport")
    public String sendMessage(@Valid Report report, Model model) {
        report.setDate(new Date());
        reportService.save(report);
        return "redirect:reportPage/reportSuccessPage";
    }

    @GetMapping("admin/reportList/delete/{reportId}")
    public String deleteReport(@PathVariable Long reportId) {
        reportService.deleteReportById(reportId);
        return "redirect:/admin/reportList";
    }
}