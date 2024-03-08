package com.jobnav.api.feature.firebase.service;

public interface NotificationService<M> {

    void sendPushNotification(final M message);

}
