package com.jobnav.api.feature.firebase.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.jobnav.api.feature.firebase.dto.JobPostNotificationMessage;
import com.jobnav.api.feature.firebase.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FirebasePushNotificationServiceImpl implements NotificationService<JobPostNotificationMessage> {

    FirebaseMessaging firebaseMessaging;

    @Override
    @SneakyThrows
    public void sendPushNotification(final JobPostNotificationMessage message) {
        log.debug("Start calling firebase messaging with message [{}]", message);
        final String response = firebaseMessaging.send(buildMessage(message, buildNotification(message)));
        log.debug("Finish calling firebase messaging with response [{}]", response);
    }

    private Notification buildNotification(final JobPostNotificationMessage message) {
        return Notification
                .builder()
                .setTitle(message.getSubject())
                .setBody(message.getContent())
                .build();
    }

    private Message buildMessage(final JobPostNotificationMessage message, final Notification notification) {
        return Message
                .builder()
                .setToken(message.getToken())
                .setNotification(notification)
                .putAllData(Map.of())
                .build();
    }


}
