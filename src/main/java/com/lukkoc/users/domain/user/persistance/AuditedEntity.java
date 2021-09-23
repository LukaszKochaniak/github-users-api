package com.lukkoc.users.domain.user.persistance;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuditedEntity implements Serializable {
    @Version
    @Column(nullable = false)
    long version = 0L;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_updated_date")
    LocalDateTime lastUpdatedDate;
}
