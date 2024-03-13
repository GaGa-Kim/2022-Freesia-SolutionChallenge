package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2LoginRequestDto {
    @ApiModelProperty(notes = "액세스 토큰", dataType = "String", example = "AaBbCc123")
    @NotEmpty
    private String accessToken;

    @Builder
    public OAuth2LoginRequestDto(String accessToken) {
        this.accessToken = accessToken;
    }
}