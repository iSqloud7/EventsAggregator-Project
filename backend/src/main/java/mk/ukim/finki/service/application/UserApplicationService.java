package mk.ukim.finki.service.application;

import mk.ukim.finki.model.dtos.user_dto.DisplayUserDTO;
import mk.ukim.finki.model.dtos.user_dto.LoginUserDTO;
import mk.ukim.finki.model.dtos.user_dto.RegisterUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {

    Optional<DisplayUserDTO> findByUsername(String username);

    Optional<DisplayUserDTO> register(RegisterUserDTO registerUserDTO);

    Optional<DisplayUserDTO> login(LoginUserDTO loginUserDTO);

    List<DisplayUserDTO> findByRole(String role);

    List<DisplayUserDTO> findByName(String name);

    List<DisplayUserDTO> findBySurname(String surname);

    List<DisplayUserDTO> findByNameAndSurname(String name, String surname);
    List<DisplayUserDTO> findAll();
}
