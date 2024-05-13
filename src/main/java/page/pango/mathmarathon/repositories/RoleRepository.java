package page.pango.mathmarathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import page.pango.mathmarathon.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
