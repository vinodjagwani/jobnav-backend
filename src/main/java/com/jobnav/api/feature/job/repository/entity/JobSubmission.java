package com.jobnav.api.feature.job.repository.entity;


import com.jobnav.api.config.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "job_submission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobSubmission extends Auditable<String> {

    static final long serialVersionUID = -337911492884105133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String jobSubmittedId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String jobId;

    @Column(nullable = false)
    String applicantId;
}
