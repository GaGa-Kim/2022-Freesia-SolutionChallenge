package com.freesia.imyourfreesia.dto.auth;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OAuth2LoginRequestDtoTest {
    public static final String SOCIAL_ACCESS_TOKEN = "12345";
    private final ValidatorUtil<OAuth2LoginRequestDto> validatorUtil = new ValidatorUtil<>();

    public static OAuth2LoginRequestDto testOAuth2LoginRequestDto() {
        return OAuth2LoginRequestDto.builder()
                .accessToken(SOCIAL_ACCESS_TOKEN)
                .build();
    }

    @Test
    @DisplayName("OAuth2LoginRequestDto 생성 테스트")
    void testOAuth2LoginRequestDtoSave() {
        OAuth2LoginRequestDto oAuth2LoginRequestDto = testOAuth2LoginRequestDto();

        assertEquals(SOCIAL_ACCESS_TOKEN, oAuth2LoginRequestDto.getAccessToken());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        OAuth2LoginRequestDto oAuth2LoginRequestDto = new OAuth2LoginRequestDto();

        assertNotNull(oAuth2LoginRequestDto);
        assertNull(oAuth2LoginRequestDto.getAccessToken());
    }

    @Test
    @DisplayName("액세스 토큰 유효성 검증 테스트")
    void accessToken_validation() {
        OAuth2LoginRequestDto oAuth2LoginRequestDto = OAuth2LoginRequestDto.builder()
                .accessToken(INVALID_EMPTY)
                .build();

        validatorUtil.validate(oAuth2LoginRequestDto);
    }
}