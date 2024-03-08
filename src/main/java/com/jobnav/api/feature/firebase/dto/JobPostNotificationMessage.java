package com.jobnav.api.feature.firebase.dto;

import lombok.Data;

@Data
public class JobPostNotificationMessage {

    String subject;

    String content;

    String token;
}
