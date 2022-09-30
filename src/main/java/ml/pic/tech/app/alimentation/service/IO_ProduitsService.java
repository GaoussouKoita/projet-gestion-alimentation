package ml.pic.tech.app.alimentation.service;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import ml.pic.tech.app.alimentation.repository.IO_ProduitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IO_ProduitsService {
    @Autowired
    private IO_ProduitsRepository io_prodsRepository;

    public void ajout(IO_Produits IOProduits) {
        io_prodsRepository.save(IOProduits);
    }

    public IO_Produits lecture(Long id) {
        return io_prodsRepository.findById(id).get();
    }

    public void suppression(Long id) {
        io_prodsRepository.deleteById(id);
    }

    public List<IO_Produits> liste() {
        return io_prodsRepository.findAll();
    }
}
