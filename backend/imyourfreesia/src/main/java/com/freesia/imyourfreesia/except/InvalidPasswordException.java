package com.freesia.imyourfreesia.except;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("비밀번호가 올바르지 않습니다. 로그인에 실패했습니다.");
    }
}