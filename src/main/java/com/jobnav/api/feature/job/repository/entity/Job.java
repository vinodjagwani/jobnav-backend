package com.jobnav.api.feature.job.repository.entity;

import com.google.common.collect.Lists;
import com.jobnav.api.config.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "job")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job extends Auditable<String> {

    static final long serialVersionUID = -337911492884105133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String jobId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String company;

    @Column(nullable = false)
    String description;

    @Column(nullable = false)
    String position;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String jobType;

    @Column(nullable = false)
    String careerPath;

    LocalDateTime expire;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "job_skills", joinColumns = @JoinColumn(name = "job_id"))
    private List<String> skills = Lists.newArrayList();


}
