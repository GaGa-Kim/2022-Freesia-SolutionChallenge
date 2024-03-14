package com.freesia.imyourfreesia.dto.auth;

import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "회원 토큰", dataType = "String", example = "token")
    private final String token;

    public TokenResponseDto(User user, String token) {
        this.email = user.getEmail();
        this.token = token;
    }
}