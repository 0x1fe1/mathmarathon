package page.pango.mathmarathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import page.pango.mathmarathon.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole getUserRoleById(Long userId);
}
