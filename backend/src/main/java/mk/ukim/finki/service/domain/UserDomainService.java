package mk.ukim.finki.service.domain;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserDomainService {

    Optional<User> findByUsername(String username);

    Optional<User> register(User user);

    Optional<User> login(String username, String password);

    List<User> findByRole(UserRole role);

    List<User> findByName(String name);

    List<User> findBySurname(String surname);

    List<User> findByNameAndSurname(String name, String surname);
}
