package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

}
