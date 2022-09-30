package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.IO_Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IO_ProduitsRepository extends JpaRepository<IO_Produits, Long> {
}
