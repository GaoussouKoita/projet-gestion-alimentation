package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.repository.DetteClientRepository;
import ml.pic.tech.app.alimentation.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetteClientService {
    @Autowired
    private DetteClientRepository detteClientRepository;

    public void ajout(DetteClient client) {
        detteClientRepository.save(client);
    }

    public DetteClient lecture(Long id) {
        return detteClientRepository.findById(id).get();
    }

    public void suppression(Long id) {
        detteClientRepository.deleteById(id);
    }

    public List<DetteClient> liste() {
        return detteClientRepository.findAll();
    }
    public Page<DetteClient> liste(int p) {
        return detteClientRepository.findAll(PageRequest.of(p, Constante.NBRE_PAR_PAGE, Sort.by("date").descending()));
    }
}
