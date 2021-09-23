package com.lukkoc.users.domain.user.persistance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserFactory {
    public UserEntity createFrom(String login) {
        return UserEntity.createUserEntityFromLogin(login);
    }
}
