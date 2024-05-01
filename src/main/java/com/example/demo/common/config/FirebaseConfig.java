package com.example.demo.common.config;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class FirebaseConfig {
    private final String firebaseKey;
    public FirebaseConfig(
            @Value("${firebase.key}") String firebaseKey
    ) {
        this.firebaseKey = firebaseKey;
    }
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        if (!FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.getInstance().delete();
        }

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseKey.getBytes(StandardCharsets.UTF_8));

        if (Objects.isNull(serviceAccount)) {
            throw new NullPointerException("service account is null");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("kkkk-3047a.appspot.com")
                .build();
        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth firebaseAuth() throws IOException {
        return FirebaseAuth.getInstance(firebaseApp());
    }

    @Bean
    public Bucket bucket() throws IOException {
        return StorageClient.getInstance(firebaseApp()).bucket();
    }


}