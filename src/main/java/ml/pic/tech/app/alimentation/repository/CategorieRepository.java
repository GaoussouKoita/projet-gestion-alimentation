package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
