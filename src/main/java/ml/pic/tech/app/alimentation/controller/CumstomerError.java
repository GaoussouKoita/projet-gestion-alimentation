package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.securite.service.AccountService;
import ml.pic.tech.app.alimentation.service.AuditService;
import ml.pic.tech.app.alimentation.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * Classe des erreurs personnalisees
 * Ici on passe les donnees du modele a la vue comme les infos
 * de l'user connecte.
 * Sinon on pouvait  juste utilise les status code comme nom
 * de vue des differentes erreurs dans le dossier error
 *
 */


@Controller
public class CumstomerError implements ErrorController {

    @Autowired
    private AccountService userService;
    @Autowired
    private AuditService auditService;


    @GetMapping(Endpoint.ERROR_ENDPOINT)
    public String notFound(HttpServletRequest request, Model model) {

        String errorPage = "erreur";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {

                errorPage = "erreur/404";

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorPage = "erreur/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorPage = "erreur/403";
            }
        }
        model.addAttribute("user", userService.currentUtilisateur());
        return errorPage;
    }

}
