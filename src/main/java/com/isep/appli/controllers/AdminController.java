package com.isep.appli.controllers;

import com.isep.appli.dbModels.Report;
import com.isep.appli.dbModels.User;
import com.isep.appli.models.FormattedReport;
import com.isep.appli.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
public class AdminController {

    private final UserService userService;
    private final ReportService reportService;
    private final PersonnageService personnageService;
    private final FamiliaService familiaService;
    private final MessageService messageService;


    public AdminController(UserService userService, ReportService reportService, PersonnageService personnageService, FamiliaService familiaService, MessageService messageService) {
        this.userService = userService;
        this.reportService = reportService;
        this.personnageService = personnageService;
        this.familiaService = familiaService;
        this.messageService = messageService;
    }

    static public String checkIsAdmin(User userAdmin, Model model) {
        if (userAdmin==null) {return "errors/error-401";}
        if (!userAdmin.getIsAdmin()) {return "errors/error-403";}
        model.addAttribute("user", userAdmin);
        return "200";
    }

    @GetMapping({"/admin/home", "/admin/", "/admin"})
    public String adminHomePage(Model model, HttpSession session) {
        // Check admin connexion
        User userAdmin = (User) session.getAttribute("user");
        String checkUser = checkIsAdmin(userAdmin, model);
        if (!checkUser.equals("200")){return checkUser;}

        // Import the html file
        return "admin/mainAdmin";
    }



    @GetMapping("/admin/users-management")
    public String usersManagementPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, HttpSession session) {
        // Check admin connexion
        User userAdmin = (User) session.getAttribute("user");
        String checkUser = checkIsAdmin(userAdmin, model);
        if (!checkUser.equals("200")){return checkUser;}

        // limit the maw size
        if (size>50) {return String.format("redirect:/admin/users-management?page=%d&size=50", page);}
        // Recover UserList
        Page<User> userPage = userService.getAllUsers(PageRequest.of(page, size));
        model.addAttribute("usersPages", userPage);

        // Add information for pagination
        int totalPages = userPage.getTotalPages();
        if (page > totalPages) {return String.format("redirect:/admin/users-management?page=%d&size=%d", userPage.getTotalPages() - 1, size);}
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/usersManagement";
    }


    @GetMapping("/admin/users-management/{id}")
    public String infoUserPage(@PathVariable long id, Model model, HttpSession session) {
        // Check admin connexion
        User userAdmin = (User) session.getAttribute("user");
        String checkUser = checkIsAdmin(userAdmin, model);
        if (!checkUser.equals("200")){return checkUser;}

        // find the user id
        User user = userService.getUserById(id);
        if (user == null) {return "errors/error-404";}
        model.addAttribute("userInfo", user);

        return "admin/userInfo";
    }

    @GetMapping("/admin/reportList")
    public String reportListPageEmpty(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return "redirect:/admin/reportList/0?page=0&size=10";
    }

    @GetMapping("/admin/reportList/{reportId}")
    public String reportListPage(@PathVariable Long reportId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model, HttpSession session) {
        // Check admin connexion
        User userAdmin = (User) session.getAttribute("user");
        String checkUser = checkIsAdmin(userAdmin, model);
        if (!checkUser.equals("200")){return checkUser;}
        // limit the maw size
        if (size>50) {return String.format("redirect:/admin/reportList/0?page=%d&size=50", page);}

        Page<FormattedReport> reportPage = reportService.getAllFormattedReportSortedByDate(PageRequest.of(page, size, Sort.by("date").descending()));
        model.addAttribute("reportPage", reportPage);

        Report reportSelected = reportService.getById(reportId);

        if (reportSelected != null) {
            model.addAttribute("reportSelected", reportService.convertToFormattedReport(reportSelected));
            if (reportSelected.getObjectReportedType().equals("MESSAGE")) {
                model.addAttribute("messageReported", messageService.getById(reportSelected.getObjectReportedId()));
            }
        }

        // Add information for pagination
        int totalPages = reportPage.getTotalPages();
        if (page > totalPages) {return String.format("redirect:/admin/reportList?page=%d&size=%d", reportPage.getTotalPages() - 1, size);}
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "admin/adminReportPage";
    }

    /*
    table pas claire entre PersonnaService et Personnage Service et create player ne fonctionne plus
    model : separer en deux model pour bdd et enumeration ?
    library, ne rien prendre sur le web, tout installer en local (subscription page)



    @GetMapping("/admin/players-management/{id}")
    public String infoPlayerPage(@PathVariable long id, Model model, HttpSession session) {
        // Check admin connexion
        User userAdmin = (User) session.getAttribute("user");
        String checkUser = checkIsAdmin(userAdmin, model);
        if (!checkUser.equals("200")){return checkUser;}

        // find the user id
        User user = userService.getUserById(id);
        if (user == null) {return "errors/error-404";}
        model.addAttribute("userInfo", user);

        return "admin/playerInfo";
    }
*/

}


/*
INSERT INTO USER values();
    id, email, enable, first_name, is_admin, last_name, password, username
INSERT INTO USER values(10, 'fictif10@yahoo.fr', 1, 'testFirst10', 0, 'testLast10', 'root', 'fictif10');
INSERT INTO USER values(11, 'fictif11@yahoo.fr', 1, 'testFirst11', 0, 'testLast11', 'root', 'fictif11');
INSERT INTO USER values(12, 'fictif12@yahoo.fr', 1, 'testFirst12', 0, 'testLast12', 'root', 'fictif12');
INSERT INTO USER values(13, 'fictif13@yahoo.fr', 1, 'testFirst13', 0, 'testLast13', 'root', 'fictif13');
INSERT INTO USER values(14, 'fictif14@yahoo.fr', 1, 'testFirst14', 0, 'testLast14', 'root', 'fictif14');
INSERT INTO USER values(15, 'fictif15@yahoo.fr', 1, 'testFirst15', 0, 'testLast15', 'root', 'fictif15');
INSERT INTO USER values(16, 'fictif16@yahoo.fr', 1, 'testFirst16', 0, 'testLast16', 'root', 'fictif16');
INSERT INTO USER values(17, 'fictif17@yahoo.fr', 1, 'testFirst17', 0, 'testLast17', 'root', 'fictif17');
INSERT INTO USER values(18, 'fictif18@yahoo.fr', 1, 'testFirst18', 0, 'testLast18', 'root', 'fictif18');
INSERT INTO USER values(19, 'fictif19@yahoo.fr', 1, 'testFirst19', 0, 'testLast19', 'root', 'fictif19');
INSERT INTO USER values(20, 'fictif20@yahoo.fr', 1, 'testFirst20', 0, 'testLast20', 'root', 'fictif20');
INSERT INTO USER values(21, 'fictif21@yahoo.fr', 1, 'testFirst21', 0, 'testLast21', 'root', 'fictif21');
INSERT INTO USER values(22, 'fictif22@yahoo.fr', 1, 'testFirst22', 0, 'testLast22', 'root', 'fictif22');
INSERT INTO USER values(23, 'fictif23@yahoo.fr', 1, 'testFirst23', 0, 'testLast23', 'root', 'fictif23');
INSERT INTO USER values(24, 'fictif24@yahoo.fr', 1, 'testFirst24', 0, 'testLast24', 'root', 'fictif24');
INSERT INTO USER values(25, 'fictif25@yahoo.fr', 1, 'testFirst25', 0, 'testLast25', 'root', 'fictif25');
INSERT INTO USER values(26, 'fictif26@yahoo.fr', 1, 'testFirst26', 0, 'testLast26', 'root', 'fictif26');
INSERT INTO USER values(27, 'fictif27@yahoo.fr', 1, 'testFirst27', 0, 'testLast27', 'root', 'fictif27');
INSERT INTO USER values(28, 'fictif28@yahoo.fr', 1, 'testFirst28', 0, 'testLast28', 'root', 'fictif28');
INSERT INTO USER values(29, 'fictif29@yahoo.fr', 1, 'testFirst29', 0, 'testLast29', 'root', 'fictif29');
INSERT INTO USER values(30, 'fictif30@yahoo.fr', 1, 'testFirst30', 0, 'testLast30', 'root', 'fictif30');
INSERT INTO USER values(31, 'fictif31@yahoo.fr', 1, 'testFirst31', 0, 'testLast31', 'root', 'fictif31');
INSERT INTO USER values(32, 'fictif32@yahoo.fr', 1, 'testFirst32', 0, 'testLast32', 'root', 'fictif32');
INSERT INTO USER values(33, 'fictif33@yahoo.fr', 1, 'testFirst33', 0, 'testLast33', 'root', 'fictif33');
INSERT INTO USER values(34, 'fictif34@yahoo.fr', 1, 'testFirst34', 0, 'testLast34', 'root', 'fictif34');
INSERT INTO USER values(35, 'fictif35@yahoo.fr', 1, 'testFirst35', 0, 'testLast35', 'root', 'fictif35');
INSERT INTO USER values(36, 'fictif36@yahoo.fr', 1, 'testFirst36', 0, 'testLast36', 'root', 'fictif36');
INSERT INTO USER values(37, 'fictif37@yahoo.fr', 1, 'testFirst37', 0, 'testLast37', 'root', 'fictif37');
INSERT INTO USER values(38, 'fictif38@yahoo.fr', 1, 'testFirst38', 0, 'testLast38', 'root', 'fictif38');
INSERT INTO USER values(39, 'fictif39@yahoo.fr', 1, 'testFirst39', 0, 'testLast39', 'root', 'fictif39');
*/