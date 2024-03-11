package com.freesia.imyourfreesia.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
    private final int port;
    private final int socketPort;
    private final boolean auth;
    private final boolean starttls;
    private final boolean startllsRequired;
    private final boolean fallback;
    private final String id;
    private final String password;

    public EmailConfig(GlobalConfig config) {
        port = config.getPort();
        socketPort = config.getSocketPort();
        auth = config.isAuth();
        starttls = config.isStarttls();
        startllsRequired = config.isStartllsRequired();
        fallback = config.isFallback();
        id = config.getAdminId();
        password = config.getAdminPassword();
    }

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setJavaMailProperties(getMailProperties());
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", socketPort);
        pt.put("mail.smtp.auth", auth);
        pt.put("mail.smtp.starttls.enable", starttls);
        pt.put("mail.smtp.starttls.required", startllsRequired);
        pt.put("mail.smtp.socketFactory.fallback", fallback);
        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return pt;
    }
}