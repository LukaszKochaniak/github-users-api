package com.lukkoc.users.domain.user.api;

import com.lukkoc.users.domain.user.service.UserFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserController {
    UserFacade userFacade;

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserByLogin(@PathVariable String login) {
        return userFacade.getUserByLogin(login);
    }
}
