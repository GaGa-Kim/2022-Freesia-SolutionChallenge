package com.freesia.imyourfreesia.domain.user;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProvider {
    GOOGLE("https://www.googleapis.com/oauth2/v3/userinfo"),
    KAKAO("https://kapi.kakao.com/v2/user/me"),
    NAVER("https://openapi.naver.com/v1/nid/me");

    private final String userInfoUrl;

    public static String findProviderInfoUrl(SocialProvider provider) {
        return Arrays.stream(SocialProvider.values())
                .filter(p -> p == provider)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported provider: " + provider))
                .getUserInfoUrl();
    }
}
