package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.DepenseService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(Endpoint.DEPENSE_ENDPOINT)
public class DepenseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private DepenseService service;
    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;

    @GetMapping(Endpoint.AJOUT_ENDPOINT)
    public String addForm(Model model, @RequestParam Long id) {
        LOGGER.info("Formulaire Depense");
        auditService.ajoutAudit(new Audit("Formulaire Depense", "Nouvelle Depense"));

        model.addAttribute("depense", new Depense());
        model.addAttribute("userId", userService.currentUtilisateur().getId());
        model.addAttribute("user", userService.currentUtilisateur());
        model.addAttribute("detteId", id);
        return "depense/ajout";
    }

    @PostMapping(Endpoint.AJOUT_ENDPOINT)
    public String add(@ModelAttribute("depense") @Valid Depense depense, Errors errors, Model model) {
        LOGGER.info("Ajout de Depense dans la bd");
        auditService.ajoutAudit(new Audit("Ajout/Update Depene", depense.toString()));
        model.addAttribute("user", userService.currentUtilisateur());

        if (errors.hasErrors()) {
            model.addAttribute("userId", userService.currentUtilisateur().getId());
            return "depense/ajout";
        } else {
            service.ajout(depense);
        }
        System.err.println(depense);
        return "redirect:liste";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        LOGGER.info("Suppression de Depense");
        auditService.ajoutAudit(new Audit("Suppression Depense", service.lecture(id).toString()));
        service.suppression(id);
        return "redirect:liste";

    }

    @GetMapping(Endpoint.INFO_ENDPOINT)
    public String rechercher(@RequestParam("id") Long id, Model model) {
        model.addAttribute("depense", service.lecture(id));
        return "depense/search";
    }

    @GetMapping(Endpoint.LISTE_ENDPOINT)
    public String all(Model model, @RequestParam(defaultValue = "0")int page) {
        LOGGER.info("Lister Depenses");
        auditService.ajoutAudit(new Audit("Liste Depense", "Depenses"));

        Page<Depense> depensePage = service.liste(page);
        model.addAttribute("depenses", depensePage.getContent());

        model.addAttribute("totalElement", depensePage.getTotalElements());
        model.addAttribute("totalPage", new int[depensePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", depensePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("user", userService.currentUtilisateur());
        return "depense/liste";
    }
}
