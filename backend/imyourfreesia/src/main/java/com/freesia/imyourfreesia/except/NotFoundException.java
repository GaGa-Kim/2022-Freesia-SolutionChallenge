package com.freesia.imyourfreesia.except;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException() {
        super("찾을 수 없습니다.");
    }
}