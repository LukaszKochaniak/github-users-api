package com.lukkoc.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = UsersApplication.class)
class UsersApplicationTest extends Specification {
    @Autowired
    UsersApplication gitUsersApplication

    def "test context loads"() {
        expect:
        gitUsersApplication != null
    }

}
