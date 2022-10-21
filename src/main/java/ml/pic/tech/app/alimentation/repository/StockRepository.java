package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Magasin;
import ml.pic.tech.app.alimentation.domaine.Produit;
import ml.pic.tech.app.alimentation.domaine.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Modifying
    @Query("UPDATE Stock s SET s.quantite=(s.quantite-?2) WHERE s.id=?1")
    public void updateByIdAndQuantiteSortie(Long id, int quantite);

    @Modifying
    @Query("UPDATE Stock s SET s.quantite=(s.quantite+?2) WHERE s.id=?1")
    public void updateByIdAndQuantiteEntree(Long id, int quantite);

    public Stock findByProduitAndMagasin(Produit p, Magasin m);

    public Stock findByProduit(Produit produit);

    Page<Stock> findByProduitNomContaining(String nom, Pageable pageable);
}
