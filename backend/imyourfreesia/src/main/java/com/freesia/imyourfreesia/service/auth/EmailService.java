package com.freesia.imyourfreesia.service.auth;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmailService {
    /**
     * 이메일 인증 코드를 전송한다.
     *
     * @param email (회원 이메일)
     * @return String (인증 코드)
     */
    String sendAuthenticationEmail(String email) throws Exception;
}