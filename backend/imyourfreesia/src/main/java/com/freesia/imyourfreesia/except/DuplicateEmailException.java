package com.freesia.imyourfreesia.except;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("이미 가입되어 있는 유저입니다.");
    }
}