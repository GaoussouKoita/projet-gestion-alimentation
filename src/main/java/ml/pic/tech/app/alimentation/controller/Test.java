package ml.pic.tech.app.alimentation.controller;

import ml.pic.tech.app.alimentation.domaine.Commande;
import ml.pic.tech.app.alimentation.service.CommandeService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Test {
    private CommandeService service;


    public Test(CommandeService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public List<Commande> commandes(){
        return service.liste();
    }
}
