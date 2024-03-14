package com.freesia.imyourfreesia.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OAuth2UserInfoRequestDtoTest {
    public static final String INVALID_EMAIL = "1234.gmail.com";
    public static final String INVALID_EMPTY = "";
    private final ValidatorUtil<OAuth2UserInfoRequestDto> validatorUtil = new ValidatorUtil<>();
    private User user;

    public static OAuth2UserInfoRequestDto testOAuth2UserInfoRequestDto(User user) {
        return OAuth2UserInfoRequestDto.builder()
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("OAuth2UserInfoRequestDto 생성 테스트")
    void testOAuth2UserInfoRequestDtoSave() {
        OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto = testOAuth2UserInfoRequestDto(user);

        assertEquals(user.getEmail(), oAuth2UserInfoRequestDto.getEmail());
        assertEquals(user.getUsername(), oAuth2UserInfoRequestDto.getName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto = new OAuth2UserInfoRequestDto();

        assertNotNull(oAuth2UserInfoRequestDto);
        assertNull(oAuth2UserInfoRequestDto.getEmail());
        assertNull(oAuth2UserInfoRequestDto.getName());
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto = OAuth2UserInfoRequestDto.builder()
                .email(INVALID_EMAIL)
                .name(user.getUsername())
                .build();

        validatorUtil.validate(oAuth2UserInfoRequestDto);
    }

    @Test
    @DisplayName("이름 유효성 검증 테스트")
    void name_validation() {
        OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto = OAuth2UserInfoRequestDto.builder()
                .email(user.getEmail())
                .name(INVALID_EMPTY)
                .build();

        validatorUtil.validate(oAuth2UserInfoRequestDto);
    }
}