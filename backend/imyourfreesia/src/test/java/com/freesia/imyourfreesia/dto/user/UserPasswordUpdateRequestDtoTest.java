package com.freesia.imyourfreesia.dto.user;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserPasswordUpdateRequestDtoTest {
    private final ValidatorUtil<UserPasswordUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private User user;

    public UserPasswordUpdateRequestDto testUserPasswordUpdateRequestDto(User user) {
        return UserPasswordUpdateRequestDto.builder()
                .password(user.getPassword())
                .build();
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("UserPasswordUpdateRequestDto 생성 테스트")
    void testUserPasswordUpdateRequestDtoSave() {
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = testUserPasswordUpdateRequestDto(user);

        assertEquals(user.getPassword(), userPasswordUpdateRequestDto.getPassword());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = new UserPasswordUpdateRequestDto();

        assertNotNull(userPasswordUpdateRequestDto);
        assertNull(userPasswordUpdateRequestDto.getPassword());
    }

    @Test
    @DisplayName("비밀번호 유효성 검증 테스트")
    void password_validation() {
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = UserPasswordUpdateRequestDto.builder()
                .password(INVALID_EMPTY)
                .build();

        validatorUtil.validate(userPasswordUpdateRequestDto);
    }
}