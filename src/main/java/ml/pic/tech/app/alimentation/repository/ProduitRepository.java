package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {

    public Produit findByCodeBarre1(Long codeBarre1);
    public List<Produit> findByNomContaining(String nom);

}