package com.tsa.dev.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    @CreatedBy
    protected Long createdBy;

    @CreatedDate
    protected Instant createdAt;

    @LastModifiedBy
    protected Long lastModifiedBy;

    @LastModifiedDate
    protected Instant updatedAt;

    protected boolean isActive = true;
}