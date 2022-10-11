package ml.pic.tech.app.alimentation.securite.repository;

import ml.pic.tech.app.alimentation.securite.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
