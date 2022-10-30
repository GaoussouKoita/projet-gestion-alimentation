package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.repository.DepenseRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DepenseService {
    @Autowired
    private DepenseRepository depenseRepository;
    @Autowired
    private DetteClientService detteService;

    public void ajout(Depense depense) {
        depense = depenseRepository.save(depense);

        DetteClient detteClient = depense.getDetteClient();
        detteClient = detteService.lecture(detteClient.getId());
        detteClient.setMontant(detteClient.getMontant() - depense.getMontant());
        detteService.ajout(detteClient);

    }

    public Depense lecture(Long id) {
        return depenseRepository.findById(id).get();
    }

    public void suppression(Long id) {
        depenseRepository.deleteById(id);
    }

    public Page<Depense> liste(int p) {
        return depenseRepository.findAll(PageRequest.of(p, Constante.NBRE_PAR_PAGE, Sort.by("date").descending()));
    }

}
