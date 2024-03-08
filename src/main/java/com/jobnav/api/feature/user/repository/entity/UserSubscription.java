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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user_subscription")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSubscription extends Auditable<String> {

    static final long serialVersionUID = -3379914934884005133L;

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    String userSubscriptionId = UUID.randomUUID().toString();

    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    String plan;

    @Column(nullable = false)
    BigDecimal amount;

    @Column(nullable = false)
    LocalDateTime startDateTime;

    @Column(nullable = false)
    LocalDateTime endDateTime;

    @Column(nullable = false)
    boolean isActive;

}
