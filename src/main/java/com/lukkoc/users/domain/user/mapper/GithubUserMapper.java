package com.lukkoc.users.domain.user.mapper;

import com.lukkoc.users.client.model.GithubUserDto;
import com.lukkoc.users.domain.user.api.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GithubUserMapper {
    GithubUserMapper INSTANCE = Mappers.getMapper(GithubUserMapper.class);

    UserResponse githubUserToUserResponse(GithubUserDto githubUserDto, Double calculations);
}
