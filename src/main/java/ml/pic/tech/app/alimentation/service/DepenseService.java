package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.Depense;
import ml.pic.tech.app.alimentation.repository.DepenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepenseService {
    @Autowired
    private DepenseRepository depenseRepository;

    public void ajout(Depense depense) {
        depenseRepository.save(depense);
    }

    public Depense lecture(Long id) {
        return depenseRepository.findById(id).get();
    }

    public void suppression(Long id) {
        depenseRepository.deleteById(id);
    }

    public Page<Depense> liste(int p) {
        return depenseRepository.findAll(PageRequest.of(p, 9, Sort.by("date").descending()));
    }
}
