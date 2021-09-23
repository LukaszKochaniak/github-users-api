package com.lukkoc.users.domain.user.service;

import com.lukkoc.users.client.GithubService;
import com.lukkoc.users.client.model.GithubUserDto;
import com.lukkoc.users.domain.user.api.UserResponse;
import com.lukkoc.users.domain.user.mapper.GithubUserMapper;
import com.lukkoc.users.domain.user.persistance.UserEntity;
import com.lukkoc.users.domain.user.persistance.UserFactory;
import com.lukkoc.users.domain.user.persistance.UserRepository;
import lombok.AllArgsConstructor;

import java.util.function.UnaryOperator;

@AllArgsConstructor
public class UserFacade {

    GithubService githubService;
    UserRepository userRepository;
    UserFactory userFactory;

    public UserResponse getUserByLogin(String login) {
        GithubUserDto githubUserDto = githubService.getUserByLogin(login);

        saveOrUpdateUserRequestCount(login);

        return GithubUserMapper.INSTANCE.githubUserToUserResponse(githubUserDto,
                provideCalculations(githubUserDto.getFollowers(), githubUserDto.getPublicRepos()));
    }

    private void saveOrUpdateUserRequestCount(String login) {
        synchronized (login.intern()) {
            UserEntity userEntity = userRepository.findByLogin(login).map(getUpdatedUser())
                    .orElseGet(() -> userFactory.createFrom(login));

            userRepository.save(userEntity);
        }
    }

    private Double provideCalculations(long followers, long publicRepos) {
        return followers != 0 ? (6d / followers * (2 + publicRepos)) : null;
    }

    private UnaryOperator<UserEntity> getUpdatedUser() {
        return user -> {
            user.updateRequestCount();
            return user;
        };
    }
}
