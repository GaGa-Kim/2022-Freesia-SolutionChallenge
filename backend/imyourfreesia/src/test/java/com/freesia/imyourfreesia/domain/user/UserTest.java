package com.freesia.imyourfreesia.domain.user;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserTest {
    public static final Long USER_ID = 1L;
    public static final String USER_NAME = "freesia";
    public static final String USER_LOGIN_ID = "freesia123";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "freesia@gmail.com";
    public static final String USER_NICKNAME = "freesia";
    public static final String USER_PROFILE_IMG = "profile.png";
    public static final String NEW_USER_NICKNAME = "reboot";
    public static final String NEW_USER_PROFILE_IMG = "reboot.png";
    private User user;

    public static User testUser() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return User.builder()
                .id(USER_ID)
                .username(USER_NAME)
                .loginId(USER_LOGIN_ID)
                .password(passwordEncoder.encode(USER_PASSWORD))
                .email(USER_EMAIL)
                .nickName(USER_NICKNAME)
                .profileImg(USER_PROFILE_IMG)
                .build();
    }

    @BeforeEach
    void setUp() {
        user = testUser();
    }

    @Test
    @DisplayName("회원 추가 테스트")
    void testUserSave() {
        assertNotNull(user);
        assertEquals(USER_ID, user.getId());
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(USER_LOGIN_ID, user.getLoginId());
        assertEquals(USER_EMAIL, user.getEmail());
        assertTrue(user.getPassword().startsWith("$2a$"));
        assertEquals(USER_NICKNAME, user.getNickName());
        assertEquals(USER_PROFILE_IMG, user.getProfileImg());
        assertEquals(Role.USER, user.getRole());
        assertTrue(user.isActivated());
    }

    @Test
    @DisplayName("회원 프로필 수정 테스트")
    void testUpdateProfile() {
        user.updateProfile(NEW_USER_NICKNAME, NEW_USER_PROFILE_IMG);

        assertEquals(NEW_USER_NICKNAME, user.getNickName());
        assertEquals(NEW_USER_PROFILE_IMG, user.getProfileImg());
    }
}