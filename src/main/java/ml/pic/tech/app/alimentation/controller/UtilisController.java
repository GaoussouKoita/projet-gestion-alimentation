package ml.pic.tech.app.alimentation.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UtilisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @GetMapping("/")
    public String home() {
        LOGGER.info("Page d'acceuil");
        return "index";
    }



    @GetMapping("/login")
    public String login() {
        LOGGER.info("Page d'authentification");
        return "login";
    }

    @GetMapping("/field")
    public String fieldDynamic() {
        LOGGER.info("Page d'authentification");
        return "field";
    }


}
