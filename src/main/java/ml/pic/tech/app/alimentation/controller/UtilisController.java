package ml.pic.tech.app.alimentation.controller;


import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private AccountService userService;

    @GetMapping(Endpoint.ACCEUIL_ENDPOINT)
    public String home(Model model) {
        LOGGER.info("Page d'acceuil");
        model.addAttribute("user", userService.currentUtilisateur());
        return "index";
    }



    @GetMapping(Endpoint.LOGIN_ENDPOINT)
    public String login() {
        LOGGER.info("Page d'Authentification");
        return "login";
    }



}
