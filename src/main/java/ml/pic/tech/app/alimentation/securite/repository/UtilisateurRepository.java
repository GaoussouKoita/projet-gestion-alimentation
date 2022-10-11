package ml.pic.tech.app.alimentation.securite.repository;

import ml.pic.tech.app.alimentation.securite.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    public Utilisateur findByLogin(String login);

    @Modifying
    @Query("UPDATE Utilisateur u SET u.password=?2 WHERE u.login=?1")
    void updateUtilisateurByLogin(String login, String newPassword);
}
