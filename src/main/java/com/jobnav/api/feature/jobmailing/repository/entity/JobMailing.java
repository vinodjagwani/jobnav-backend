package com.jobnav.api.feature.jobmailing.repository.entity;

import com.jobnav.api.config.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "job_mailing", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobMailing extends Auditable<String> {

    static final long serialVersionUID = -337921492884005133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String jobMailingId = UUID.randomUUID().toString();

    @Column(unique = true, nullable = false)
    String email;
}
