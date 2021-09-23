package com.lukkoc.users.client;

import com.lukkoc.users.client.model.GithubUserDto;
import com.lukkoc.users.infrastructure.config.feign.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "github-api", url="${client.github.url}", configuration = FeignClientConfiguration.class)
interface GithubClient {
    @GetMapping(value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    GithubUserDto getUserByLogin(@PathVariable String login);
}
