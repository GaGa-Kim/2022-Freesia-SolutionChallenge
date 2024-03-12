package com.freesia.imyourfreesia.service.auth;

public interface EmailService {
    String sendAuthMail(String to) throws Exception;
}