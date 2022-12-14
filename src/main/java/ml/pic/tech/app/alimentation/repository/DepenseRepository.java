package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Depense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

}
