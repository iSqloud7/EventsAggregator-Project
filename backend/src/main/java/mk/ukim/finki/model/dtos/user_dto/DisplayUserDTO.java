package mk.ukim.finki.model.dtos.user_dto;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayUserDTO(

        Long ID,
        String username,
        String password,
        String name,
        String surname,
        UserRole role
) {
    public static DisplayUserDTO from(User user) {
        return new DisplayUserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }

    public static List<DisplayUserDTO> from(List<User> users) {
        return users.stream()
                .map(DisplayUserDTO::from)
                .collect(Collectors.toList());
    }

    public User toUser() {
        return new User(username, password, name, surname, role);
    }
}
