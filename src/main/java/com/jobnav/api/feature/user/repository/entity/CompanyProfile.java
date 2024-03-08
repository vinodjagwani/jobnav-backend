package com.jobnav.api.feature.user.repository.entity;

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
@Table(name = "company_profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyProfile extends Auditable<String> {

    static final long serialVersionUID = -347991492884005133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String companyProfileId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String aboutCompany;

    @Column
    String companyLogo;

    @Column(nullable = false)
    String userId;


}
