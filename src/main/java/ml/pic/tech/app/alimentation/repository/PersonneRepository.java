package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {
}
