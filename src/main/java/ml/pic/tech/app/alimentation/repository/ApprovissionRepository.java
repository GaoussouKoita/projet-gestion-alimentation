package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Approvission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovissionRepository extends JpaRepository<Approvission, Long> {
}