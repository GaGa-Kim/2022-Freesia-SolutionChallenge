package com.freesia.imyourfreesia.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocialProviderTest {
    @Test
    @DisplayName("구글 소셜 로그인 제공자 열거형 값 테스트")
    void testGoogleSocialProvider() {
        SocialProvider provider = SocialProvider.GOOGLE;

        assertEquals(SocialProvider.GOOGLE.getUserInfoUrl(), provider.getUserInfoUrl());
    }

    @Test
    @DisplayName("카카오 소셜 로그인 제공자 열거형 값 테스트")
    void testKakaoSocialProvider() {
        SocialProvider provider = SocialProvider.KAKAO;

        assertEquals(SocialProvider.KAKAO.getUserInfoUrl(), provider.getUserInfoUrl());
    }

    @Test
    @DisplayName("네이버 소셜 로그인 제공자 열거형 값 테스트")
    void testNaverSocialProvider() {
        SocialProvider provider = SocialProvider.NAVER;

        assertEquals(SocialProvider.NAVER.getUserInfoUrl(), provider.getUserInfoUrl());
    }
}