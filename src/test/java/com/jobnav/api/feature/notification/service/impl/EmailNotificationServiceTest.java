package com.jobnav.api.feature.notification.service.impl;

import com.jobnav.api.feature.notification.dto.EmailDataRequest;
import com.jobnav.api.feature.notification.dto.NotificationType;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EmailNotificationServiceTest {

    @Mock
    JavaMailSender mailSender;

    @Mock
    Configuration freemarkerConfig;

    @InjectMocks
    EmailNotificationService emailNotificationService;

    @Test
    @SneakyThrows
    void testPrepareAndSendMessage() {
        final MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(freemarkerConfig.getTemplate(any(String.class))).thenReturn(new Template("", "", new Configuration(Configuration.VERSION_2_3_31)));
        doNothing().when(mailSender).send(any(MimeMessage.class));
        emailNotificationService.prepareAndSendMessage(buildEmailDataRequest(), NotificationType.EMAIL, Locale.ENGLISH, "");
        verify(mailSender, atLeastOnce()).createMimeMessage();
        verify(freemarkerConfig, atLeastOnce()).getTemplate(any(String.class));
        verify(mailSender, atLeastOnce()).send(any(MimeMessage.class));
    }

    private EmailDataRequest buildEmailDataRequest() {
        return EmailDataRequest.builder()
                .to(List.of("test@gmail.com"))
                .subject("test")
                .content(Map.of("test", "test"))
                .from("test@gmail.com")
                .build();

    }
}
