package com.jobnav.api.feature.user.repository.entity;

import com.google.common.collect.Lists;
import com.jobnav.api.config.audit.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "account_user_profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfile extends Auditable<String> {

    static final long serialVersionUID = -3379923392884005133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String userProfileId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String language;

    @Column(nullable = false)
    String mobile;

    @Column(columnDefinition = "text")
    String resumeJson;

    @Column
    String photo;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "applicant_skills", joinColumns = @JoinColumn(name = "user_profile_id"))
    private List<String> skills = Lists.newArrayList();

    @Column
    int workExperience;

    @Column(nullable = false)
    String position;

    @Column
    LocalDate startDate;

    boolean isCurrentEmployed;

    boolean isAvailable;

    boolean isRemote;

    boolean visaRequiredCurrent;

    boolean visaRequiredFuture;

    @OneToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "account_user", referencedColumnName = "userId")
    User user;

}
