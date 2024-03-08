package com.jobnav.api.feature.user.repository.entity;

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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "job_applicant_contact_form")
public class JobApplicantContactForm {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String jobApplicantContactFormId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false, columnDefinition = "TEXT")
    String message;


}
