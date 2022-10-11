package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

//    @Query(value = "SELECT com.id, com.date, com.statut_commande, p.id, p.nom, " +
//            "p.prenom, p.type_compte, u.id, u.login FROM commande com, " +
//            "tb_client_fournisseur p, user u " +
//            "where com.user_id=u.id and com.personne_id=p.id", nativeQuery = true)
//    public List<Commande> commandes();

//    @Query(value = "Select c.id, c.date, c.statutCommande, p.id, p.nom, p.prenom, u.id, u.login " +
//            "FROM Commande c, Personne p, Utilisateur u where c.personne.id=p.id and c.user.id=u.id")
//
//    @Query("SELCT * FROM Commande c")
//    public Iterator<Commande> commandes();
}
