package com.freesia.imyourfreesia.except;

public class InvalidProviderException extends IllegalArgumentException {
    public InvalidProviderException() {
        super("지원하지 않는 로그인 공급자입니다. 구글, 카카오, 네이버 로그인만 가능합니다.");
    }
}
