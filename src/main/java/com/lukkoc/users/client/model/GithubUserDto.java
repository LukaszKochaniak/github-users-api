package com.lukkoc.users.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GithubUserDto {
    long id;
    String login;
    String name;
    String type;
    @JsonProperty("avatar_url")
    String avatarUrl;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    long followers;
    @JsonProperty("public_repos")
    long publicRepos;
}
