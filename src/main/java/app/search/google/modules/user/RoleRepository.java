package app.search.google.modules.user;

import app.search.google.modules.user.domain.Role;
import app.search.google.modules.user.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleType roleType);
}