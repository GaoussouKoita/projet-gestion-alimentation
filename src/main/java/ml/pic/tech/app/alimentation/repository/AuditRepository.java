package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    public Page<Audit> findByUtilisateurId(Long id, Pageable pageable);
}
