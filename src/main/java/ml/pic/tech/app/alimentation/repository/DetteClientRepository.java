package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.DetteClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetteClientRepository extends JpaRepository<DetteClient, Long> {
}
