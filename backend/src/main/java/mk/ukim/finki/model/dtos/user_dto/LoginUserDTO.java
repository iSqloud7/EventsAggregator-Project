package mk.ukim.finki.model.dtos.user_dto;

import mk.ukim.finki.model.entities.User;

public record LoginUserDTO(

        String username,
        String password
) {
    public User toUser() {
        return new User(username, password);
    }
}
