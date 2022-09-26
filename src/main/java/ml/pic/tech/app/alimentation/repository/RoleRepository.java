package ml.pic.tech.app.alimentation.repository;

import ml.pic.tech.app.alimentation.domaine.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
