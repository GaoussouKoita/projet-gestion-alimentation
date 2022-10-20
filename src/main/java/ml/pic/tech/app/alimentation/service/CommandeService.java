package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Commande;
import ml.pic.tech.app.alimentation.repository.CommandeRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    public void ajout(Commande commande) {
        commandeRepository.save(commande);
    }

    public Commande lecture(Long id) {
        return commandeRepository.findById(id).get();
    }

    public void suppression(Long id) {
        commandeRepository.deleteById(id);
    }

    public Page<Commande> liste(int p) {
        return commandeRepository.findAll(PageRequest.of(p, Constante.NBRE_PAR_PAGE, Sort.by("date").descending()));
    }
}
