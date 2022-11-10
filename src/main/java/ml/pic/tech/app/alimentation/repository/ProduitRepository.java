package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    public Produit findByCodeBarre1OrCodeBarre2(Long codeBarre1, Long codeBarre2);

    public Page<Produit> findByNomContaining(String nom, Pageable pageable);

    Produit findByNom(String nom);
}
