package com.freesia.imyourfreesia.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenResponseDtoTest {
    public final static String TOKEN = "token";
    private User user;

    public static TokenResponseDto testTokenResponseDto(User user) {
        return new TokenResponseDto(user, TOKEN);
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("TokenResponseDto 생성 테스트")
    void testTokenResponseDtoSave() {
        TokenResponseDto tokenResponseDto = testTokenResponseDto(user);

        assertEquals(user.getEmail(), tokenResponseDto.getEmail());
        assertEquals(TOKEN, tokenResponseDto.getToken());
    }
}