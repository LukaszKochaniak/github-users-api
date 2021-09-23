package com.lukkoc.users.client;

import com.lukkoc.users.client.model.GithubUserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GithubService {
    GithubClient githubClient;

    public GithubUserDto getUserByLogin(String login) {
        return githubClient.getUserByLogin(login);
    }
}
