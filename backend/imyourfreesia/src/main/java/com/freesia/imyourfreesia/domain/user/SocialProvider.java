package com.freesia.imyourfreesia.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProvider {
    GOOGLE("https://www.googleapis.com/oauth2/v3/userinfo"),
    KAKAO("https://kapi.kakao.com/v2/user/me"),
    NAVER("https://openapi.naver.com/v1/nid/me");

    private final String userInfoUrl;
}