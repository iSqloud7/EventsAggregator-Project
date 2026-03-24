package mk.ukim.finki.model.dtos.user_dto;

import mk.ukim.finki.model.entities.User;
import mk.ukim.finki.model.enumerations.UserRole;

public record RegisterUserDTO(

        String username,
        String password,
        String name,
        String surname,
        UserRole userRole
) {
    public User toUser() {
        return new User(username, password, name, surname, userRole);
    }
}
