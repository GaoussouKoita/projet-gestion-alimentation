package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Audit;
import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.service.StockService;
import ml.pic.tech.app.alimentation.service.VenteService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.LOGIN_ENDPOINT)
    public String login() {
        LOGGER.info("Page d'Authentification");
        auditService.ajoutAudit(new Audit("Demande Authentification", "Login page"));
        return "login";
    }
}
