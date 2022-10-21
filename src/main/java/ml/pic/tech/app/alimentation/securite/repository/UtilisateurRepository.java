package ml.pic.tech.app.alimentation.securite.repository;

import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByEmail(String email);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.password=?2 WHERE u.email=?1")
    void updateUtilisateurByLogin(String email, String newPassword);

    Page<Utilisateur> findByNomContaining(String nom, Pageable pageable);
}
