package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    Page<Vente> findByDate(LocalDate date,Pageable pageable);
    List<Vente> findByDate(LocalDate date);

    List<Vente> findByDateBetween(LocalDate date1, LocalDate date2);
}
