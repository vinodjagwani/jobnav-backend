package com.jobnav.api.config.audit;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U extends Serializable> implements Serializable {

    @CreatedBy
    @Column(length = 36, updatable = false)
    U createdByUser;

    @CreatedDate
    @Column(length = 26, updatable = false)
    LocalDateTime createdDatetime;

    @LastModifiedBy
    @Column(length = 36)
    U updatedByUser;

    @Column(length = 20)
    LocalDateTime updatedDatetime;

}
