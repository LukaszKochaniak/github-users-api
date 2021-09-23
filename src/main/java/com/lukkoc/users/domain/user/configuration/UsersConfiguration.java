package com.lukkoc.users.domain.user.configuration;

import com.lukkoc.users.client.GithubService;
import com.lukkoc.users.domain.user.persistance.UserRepository;
import com.lukkoc.users.domain.user.persistance.UserFactory;
import com.lukkoc.users.domain.user.service.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UsersConfiguration {
    @Bean
    UserFacade usersFacade(final GithubService githubService, final UserRepository userRepository) {
        return new UserFacade(githubService, userRepository, new UserFactory());
    }
}
