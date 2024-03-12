package com.freesia.imyourfreesia.dto.auth;

import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    @ApiModelProperty(notes = "이메일")
    private final String email;

    @ApiModelProperty(notes = "토큰")
    private final String token;

    public TokenResponseDto(User user, String token) {
        this.email = user.getEmail();
        this.token = token;
    }
}