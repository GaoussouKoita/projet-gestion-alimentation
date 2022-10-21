package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Approvision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface ApprovisionRepository extends JpaRepository<Approvision, Long> {
    Page<Approvision> findByDate(LocalDate date, Pageable pageable);

}
