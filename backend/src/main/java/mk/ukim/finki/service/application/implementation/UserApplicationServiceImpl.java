package mk.ukim.finki.service.application.implementation;

import mk.ukim.finki.model.dtos.user_dto.DisplayUserDTO;
import mk.ukim.finki.model.dtos.user_dto.LoginUserDTO;
import mk.ukim.finki.model.dtos.user_dto.RegisterUserDTO;
import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;
import mk.ukim.finki.service.application.UserApplicationService;
import mk.ukim.finki.service.domain.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserDomainService userDomainService;

    public UserApplicationServiceImpl(UserDomainService userDomainService) {
        this.userDomainService = userDomainService;
    }

    @Override
    public Optional<DisplayUserDTO> findByUsername(String username) {
        return this.userDomainService.findByUsername(username)
                .map(DisplayUserDTO::from);
    }

    @Override
    public Optional<DisplayUserDTO> register(RegisterUserDTO registerUserDTO) {
        User user = new User(
                registerUserDTO.username(),
                registerUserDTO.password(),
                registerUserDTO.name(),
                registerUserDTO.surname(),
                UserRole.CLIENT
        );

        return this.userDomainService.register(user)
                .map(DisplayUserDTO::from);
    }

    @Override
    public Optional<DisplayUserDTO> login(LoginUserDTO loginUserDTO) {
        return this.userDomainService.login(loginUserDTO.username(), loginUserDTO.password())
                .map(DisplayUserDTO::from);
    }

    @Override
    public List<DisplayUserDTO> findByRole(String role) {
        return DisplayUserDTO.from(
                this.userDomainService.findByRole(UserRole.valueOf(role))
        );
    }

    @Override
    public List<DisplayUserDTO> findByName(String name) {
        return DisplayUserDTO.from(
                this.userDomainService.findByName(name)
        );
    }

    @Override
    public List<DisplayUserDTO> findBySurname(String surname) {
        return DisplayUserDTO.from(
                this.userDomainService.findBySurname(surname)
        );
    }

    @Override
    public List<DisplayUserDTO> findByNameAndSurname(String name, String surname) {
        return DisplayUserDTO.from(
                this.userDomainService.findByNameAndSurname(name, surname)
        );
    }
}
