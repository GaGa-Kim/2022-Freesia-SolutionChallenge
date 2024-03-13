package com.freesia.imyourfreesia.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, "정상적으로 처리되었습니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 소셜 로그인 액세스 토큰입니다."),

    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    JWT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),

    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없습니다."),

    DUPLICATE_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입되어 있는 이메일입니다."),
    EMAIL_AUTH_CODE_NOT_SEND(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 인증 코드 전송에 실패했습니다."),
    INVALID_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호가 올바르지 않습니다. 로그인에 실패했습니다."),
    INVALID_PROVIDER(HttpStatus.INTERNAL_SERVER_ERROR, "지원하지 않는 로그인 공급자입니다. 구글, 카카오, 네이버 로그인만 가능합니다."),
    USER_NOT_ACTIVATED(HttpStatus.INTERNAL_SERVER_ERROR, "비활성화된 회원입니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예기치 못한 오류가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}