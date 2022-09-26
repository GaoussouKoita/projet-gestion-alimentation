package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.DetteClient;
import ml.pic.tech.app.alimentation.repository.DetteClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
