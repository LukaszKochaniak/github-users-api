package com.lukkoc.users.domain.user.persistance;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user", schema = "public")
@Getter
@NoArgsConstructor
public class UserEntity extends AuditedEntity {
    @Id
    @Column(nullable = false, updatable = false)
    String login;

    @Column(name = "request_count", nullable = false)
    long requestCount;

    private UserEntity(final String login) {
        this.login = login;
        this.requestCount = 1;
    }

    static UserEntity createUserEntityFromLogin(String login) {
        return new UserEntity(login);
    }

    public void updateRequestCount() {
        this.requestCount++;
    }
}
