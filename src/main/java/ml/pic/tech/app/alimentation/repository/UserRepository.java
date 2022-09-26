package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByLogin(String login);
}
