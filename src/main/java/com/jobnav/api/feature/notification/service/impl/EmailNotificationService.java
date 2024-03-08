package com.jobnav.api.feature.notification.service.impl;

import com.jobnav.api.feature.notification.dto.EmailDataRequest;
import com.jobnav.api.feature.notification.dto.NotificationType;
import com.jobnav.api.feature.notification.service.NotificationService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmailNotificationService implements NotificationService<EmailDataRequest> {

    JavaMailSender mailSender;

    Configuration freemarkerConfig;

    @Override
    @SneakyThrows
    public void prepareAndSendMessage(final EmailDataRequest data, final NotificationType notificationType, final Locale locale, final String templateName) {
        log.info("Start Sending email to: {} with content: {}", data.getTo(), data.getContent());
        final MimeMessage message = mailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        helper.setFrom(data.getFrom());
        helper.setTo(data.getTo().toArray(new String[0]));
        helper.setSubject(data.getSubject());
        final Template template = freemarkerConfig.getTemplate("/" + notificationType.name().toLowerCase() + "/" + locale.toString() + templateName);
        helper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(template, data.getContent()), true);
        mailSender.send(message);
        log.info("End Sending email to: {} with content: {}", data.getTo(), data.getContent());
    }
}
