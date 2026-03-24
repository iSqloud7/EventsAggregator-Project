package mk.ukim.finki.repository;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRole(UserRole role);

    List<User> findByNameContainingIgnoreCase(String name);

    List<User> findBySurnameContainingIgnoreCase(String surname);

    List<User> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);
}
