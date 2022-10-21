package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.RetourProduit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetourProduitRepository extends JpaRepository<RetourProduit, Long> {
    Page<RetourProduit> findByProduitNomContaining(String nom, Pageable pageable);

}
