package com.freesia.imyourfreesia.except;

public class UserNotActivatedException extends RuntimeException {
    public UserNotActivatedException() {
        super("비활성화된 유저입니다.");
    }
}