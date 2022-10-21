package ml.pic.tech.app.alimentation.controller.api;

import ml.pic.tech.app.alimentation.domaine.Approvision;
import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.service.ApprovisionService;
import ml.pic.tech.app.alimentation.service.IO_ProduitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApprovisionRest {

    @Autowired
    private ApprovisionService service;
    @Autowired
    private IO_ProduitsService io_produitsService;


    @PostMapping(value = "/appro/add")
    public void addAppro(@RequestBody Approvision approvision) {
        service.ajout(approvision);
    }


    @GetMapping(value = "/appro/liste", produces = "application/json" )
    public List<Approvision> list() {
        return service.liste();
    }


    @GetMapping(value = "/prods/liste", produces = "application/json")
    public List<IO_Produits> produits() {
        return io_produitsService.liste();
    }

}
