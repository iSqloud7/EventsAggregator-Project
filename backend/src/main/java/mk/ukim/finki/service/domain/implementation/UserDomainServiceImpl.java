package mk.ukim.finki.service.domain.implementation;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;
import mk.ukim.finki.repository.UserRepository;
import mk.ukim.finki.service.domain.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;

    public UserDomainServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> register(User user) {
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public Optional<User> login(String username, String password) {
        if (username == null || password == null) return Optional.empty();

        return this.userRepository.findByUsername(username.trim())
                .filter(user -> user.getPassword().trim().equals(password.trim()));
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return this.userRepository.findByRole(role);
    }

    @Override
    public List<User> findByName(String name) {
        return this.userRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<User> findBySurname(String surname) {
        return this.userRepository.findBySurnameContainingIgnoreCase(surname);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findByNameAndSurname(String name, String surname) {
        return this.userRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname);
    }
}
