package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.TransfertStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransfertStockRepository extends JpaRepository<TransfertStock, Long> {
}
