package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.RetourProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetourProduitRepository extends JpaRepository<RetourProduit, Long> {
}
