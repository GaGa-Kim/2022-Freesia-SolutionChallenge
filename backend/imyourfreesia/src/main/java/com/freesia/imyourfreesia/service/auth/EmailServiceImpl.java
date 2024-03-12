package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.config.GlobalConfig;
import com.freesia.imyourfreesia.except.EmailSendingException;
import java.util.Random;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final GlobalConfig config;
    private final JavaMailSender emailSender;

    @Override
    public String sendAuthMail(String email) throws Exception {
        String authCode = generateAuthCode();
        MimeMessage message = createAuthMail(email, authCode);
        sendEmail(message);
        return authCode;
    }

    private String generateAuthCode() {
        Random random = new Random();
        return random.ints(8, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private MimeMessage createAuthMail(String email, String authCode) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("I'm your freesia Membership Registration Authentication");
        String mailBody = createMailBody(authCode);
        message.setText(mailBody, "utf-8", "html");
        message.setFrom(new InternetAddress(config.getAdminId(), "I'm your freesia"));
        return message;
    }

    private void sendEmail(MimeMessage message) {
        try {
            emailSender.send(message);
        } catch (MailException e) {
            log.error(e.getMessage());
            throw new EmailSendingException();
        }
    }

    private String createMailBody(String authCode) {
        String mailBody = "<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 500px; height: 600px; border-top: 4px solid #FFEF5E; margin: 100px auto; padding: 30px 0; box-sizing: border-box\";>";
        mailBody += "<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">";
        mailBody += "<span style=\"font-size: 15px; margin: 0 0 10px 3px;\"><strong>I'm your freesia</strong></span><br />";
        mailBody += "<span style=\"color: #FFEF5E\"><strong>Membership authentication</strong></span>";
        mailBody += "</h1><br />";
        mailBody += "<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\"> Hello, I'm your freesia. <br />Please enter the code below in the membership authentication. <br />Thank you.</p><br />";
        mailBody += "<div align='center'>";
        mailBody +=
                " <p align='center' style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #FFEF5E; line-height: 45px; vertical-align: middle; font-size: 16px;\"><strong>"
                        + authCode + "</strong></p<br/>";
        mailBody += "</div><br/>";
        mailBody += "<<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>";
        mailBody += "</div>";
        return mailBody;
    }
}
