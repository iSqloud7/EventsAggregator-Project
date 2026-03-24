package mk.ukim.finki.controller;

import mk.ukim.finki.model.dtos.user_dto.DisplayUserDTO;
import mk.ukim.finki.model.dtos.user_dto.LoginUserDTO;
import mk.ukim.finki.model.dtos.user_dto.RegisterUserDTO;
import mk.ukim.finki.service.application.UserApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/user-register")
    public DisplayUserDTO register(@RequestBody RegisterUserDTO registerUserDTO) {

        return this.userApplicationService.register(registerUserDTO)
                .orElseThrow(() -> new RuntimeException("Register failed!"));
    }

    @PostMapping("/user-login")
    public DisplayUserDTO login(@RequestBody LoginUserDTO loginUserDTO) {

        return this.userApplicationService.login(loginUserDTO)
                .orElseThrow(() -> new RuntimeException("Invalid credentials!"));
    }

    @GetMapping("/by-role")
    public List<DisplayUserDTO> byRole(@RequestParam String role) {

        return this.userApplicationService.findByRole(role);
    }

    @GetMapping("/by-name")
    public List<DisplayUserDTO> byName(@RequestParam String name) {

        return this.userApplicationService.findByName(name);
    }

    @GetMapping("/by-surname")
    public List<DisplayUserDTO> bySurname(@RequestParam String surname) {

        return this.userApplicationService.findBySurname(surname);
    }

    @GetMapping("/by-name-and-surname")
    public List<DisplayUserDTO> byNameAndSurname(@RequestParam String name,
                                                 @RequestParam String surname) {

        return this.userApplicationService.findByNameAndSurname(name, surname);
    }
}
