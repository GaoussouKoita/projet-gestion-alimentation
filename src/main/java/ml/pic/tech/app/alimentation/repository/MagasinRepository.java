package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {
}
