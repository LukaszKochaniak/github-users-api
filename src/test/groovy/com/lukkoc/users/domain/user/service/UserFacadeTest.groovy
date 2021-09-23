package com.lukkoc.users.domain.user.service

import com.lukkoc.users.client.GithubService
import com.lukkoc.users.domain.user.persistance.UserFactory
import com.lukkoc.users.domain.user.persistance.UserRepository
import com.lukkoc.users.util.EntityUtil
import spock.lang.Specification
import spock.lang.Subject

class UserFacadeTest extends Specification {

    private static final String ZERO_FOLLOWERS_LOGIN = "zeroFollowers";
    private static final String NON_ZERO_FOLLOWERS_LOGIN = "nonZeroFollowers";

    @Subject
    UserFacade gitUsersService

    UserFactory userFactory

    GithubService githubService = Stub(GithubService) {
        getUserByLogin(ZERO_FOLLOWERS_LOGIN) >> EntityUtil.getZeroFollowersGitUserDto()
        getUserByLogin(NON_ZERO_FOLLOWERS_LOGIN) >> EntityUtil.getGitUserDto()
    }

    UserRepository userRepository = Mock()

    def setup() {
        userFactory = new UserFactory()
        gitUsersService = new UserFacade(githubService, userRepository, userFactory)
    }

    def 'should return if terms are accepted by user'(String login) {
        given:
        userRepository.findByLogin(login) >> Optional.of(userFactory.createFrom(login))

        when:
        def result = gitUsersService.getUserByLogin(login)

        then:
        result.getCalculations() == expectedResult

        where:
        login                    || expectedResult
        ZERO_FOLLOWERS_LOGIN     || null
        NON_ZERO_FOLLOWERS_LOGIN || 18.0
    }
}
