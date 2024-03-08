package com.jobnav.api.feature.notification.service;


import com.jobnav.api.feature.notification.dto.NotificationType;

import java.util.Locale;

@FunctionalInterface
public interface NotificationService<T> {

    void prepareAndSendMessage(final T content, final NotificationType notificationType, final Locale locale, final String template);

}
