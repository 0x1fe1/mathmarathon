package page.pango.mathmarathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import page.pango.mathmarathon.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByName(String username);

    User getUserByName(String username);

    boolean existsByName(String username);
}