package com.jobnav.api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

    @Bean
    @SneakyThrows
    public FirebaseMessaging firebaseMessaging() {
        final GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase/service-account-firebase.json").getInputStream());
        final FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        final FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions, "my-app");
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
