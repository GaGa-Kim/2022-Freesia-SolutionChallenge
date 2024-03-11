package com.freesia.imyourfreesia.config;

import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Getter
public class GlobalConfig {
    @Autowired
    private Environment environment;

    private String secret;
    private int expirationTime;

    private int port;
    private int socketPort;
    private boolean auth;
    private boolean starttls;
    private boolean startllsRequired;
    private boolean fallback;
    private String adminId;
    private String adminPassword;

    private String youtubeKey;

    @PostConstruct
    public void init() {
        initJWT();
        initEmail();
        initYoutube();
    }

    private void initJWT() {
        secret = environment.getProperty("jwt.secret");
        expirationTime = Integer.parseInt(Objects.requireNonNull(environment.getProperty("jwt.expiration_time")));
    }

    private void initEmail() {
        port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("mail.smtp.port")));
        socketPort = Integer.parseInt(Objects.requireNonNull(environment.getProperty("mail.smtp.socketFactory.port")));
        auth = Boolean.parseBoolean(environment.getProperty("mail.smtp.auth"));
        starttls = Boolean.parseBoolean(environment.getProperty("mail.smtp.starttls.enable"));
        startllsRequired = Boolean.parseBoolean(environment.getProperty("mail.smtp.starttls.required"));
        fallback = Boolean.parseBoolean(environment.getProperty("mail.smtp.socketFactory.fallback"));
        adminId = environment.getProperty("admin.id");
        adminPassword = environment.getProperty("admin.password");
    }

    private void initYoutube() {
        youtubeKey = environment.getProperty("youtube.apikey");
    }
}