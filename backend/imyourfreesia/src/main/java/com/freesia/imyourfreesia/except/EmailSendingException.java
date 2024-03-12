package com.freesia.imyourfreesia.except;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException() {
        super("이메일 인증 코드 전송에 실패했습니다.");
    }
}
