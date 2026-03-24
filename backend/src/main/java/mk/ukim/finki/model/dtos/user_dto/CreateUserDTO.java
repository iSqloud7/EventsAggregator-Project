package mk.ukim.finki.model.dtos.user_dto;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public record CreateUserDTO(

        String username,
        String password,
        String name,
        String surname,
        UserRole role
) {

    public static CreateUserDTO from(User user) {
        return new CreateUserDTO(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }

    public static List<CreateUserDTO> from(List<User> users) {
        return users.stream()
                .map(CreateUserDTO::from)
                .collect(Collectors.toList());
    }

    public User toUser() {
        return new User(username, password, name, surname, role);
    }
}
