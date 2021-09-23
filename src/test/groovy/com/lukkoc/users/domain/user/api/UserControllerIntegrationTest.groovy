package com.lukkoc.users.domain.user.api


import com.lukkoc.users.client.GithubService
import com.lukkoc.users.domain.user.mapper.GithubUserMapper
import com.lukkoc.users.domain.user.persistance.UserRepository
import com.lukkoc.users.util.EntityUtil
import feign.FeignException
import feign.Request
import feign.RequestTemplate
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import spock.lang.Specification

@EnableWebMvc
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class UserControllerIntegrationTest extends Specification {

    private static final String EXISTING_USER_LOGIN = "ExistingUser"

    @Autowired
    MockMvc mvc

    @Autowired
    UserRepository userRepository

    @SpringBean
    GithubService githubService = Stub(GithubService) {
        getUserByLogin("ExistingUser") >> EntityUtil.getGitUserDto()
        getUserByLogin("NotFoundUser") >> { throw new FeignException.NotFound("FeignException.NotFound", Request.create(Request.HttpMethod.GET, "https://api.github.com/users", new HashMap<>(), null, new RequestTemplate()), null) }
        getUserByLogin("InternalServerErrorUser") >> { throw new FeignException.InternalServerError("FeignException.InternalServerError", Request.create(Request.HttpMethod.GET, "https://api.github.com/users", new HashMap<>(), null, new RequestTemplate()), "{\"status\":\"Server error\"}\n".getBytes()) }
    }

    def "GitUser by login - should return valid response"() {
        when:
        String result = mvc.perform(MockMvcRequestBuilders.get("/users/" + EXISTING_USER_LOGIN))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        userRepository.findById(EXISTING_USER_LOGIN).get().getRequestCount() == 1
        EntityUtil.getGitUserResponse(result) == GithubUserMapper.INSTANCE.githubUserToUserResponse(EntityUtil.getGitUserDto(), 18.0)
    }

    def "GitUser by login - should return HTTP 404 when FeignException.NotFound is thrown"() {
        expect:
        mvc.perform(MockMvcRequestBuilders.get("/users/NotFoundUser"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    def "GitUser by login - should return HTTP 502 when FeignException.InternalServerError is thrown"() {
        expect:
        mvc.perform(MockMvcRequestBuilders.get("/users/InternalServerErrorUser"))
                .andExpect(MockMvcResultMatchers.status().isBadGateway())
    }
}
