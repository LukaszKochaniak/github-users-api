package com.lukkoc.users.util


import com.fasterxml.jackson.databind.ObjectMapper
import com.lukkoc.users.client.model.GithubUserDto
import com.lukkoc.users.domain.user.api.UserResponse

class EntityUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules()

    static UserResponse getGitUserResponse(String content) {
        return getResponse(content, UserResponse)
    }

    static UserResponse getGitUserResponse() {
        return getResponse(getGitUserResponseString(), UserResponse)
    }

    static GithubUserDto getGitUserDto() {
        return getResponse(getGitUserDtoString(), GithubUserDto)
    }

    static GithubUserDto getZeroFollowersGitUserDto() {
        return getResponse(getZeroFollowersGitUserDtoString(), GithubUserDto)
    }

    private static String getGitUserResponseString() {
        return "{\"id\":583231,\"login\":\"octocat\",\"name\":\"The Octocat\",\"type\":\"User\",\"avatarUrl\":\"https://avatars.githubusercontent.com/u/583231?v=4\",\"createdAt\":\"2011-01-25T18:44:36\",\"calculations\":0.0}"
    }

    private static String getGitUserDtoString() {
        return "{\"login\":\"octocat\",\"id\":583231,\"avatar_url\":\"https://avatars.githubusercontent.com/u/583231?v=4\",\"type\":\"User\",\"name\":\"The Octocat\",\"public_repos\":1,\"followers\":1,\"created_at\":\"2011-01-25T18:44:36Z\"}"
    }

    private static String getZeroFollowersGitUserDtoString() {
        return "{\"login\":\"octocat\",\"id\":583231,\"avatar_url\":\"https://avatars.githubusercontent.com/u/583231?v=4\",\"type\":\"User\",\"name\":\"The Octocat\",\"public_repos\":8,\"followers\":0,\"created_at\":\"2011-01-25T18:44:36Z\"}"
    }

    private static <T> T getResponse(String content, Class<T> clazz) {
        return objectMapper.readValue(content, clazz)
    }

}
