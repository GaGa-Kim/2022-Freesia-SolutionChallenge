package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserInfoDto {
    @ApiModelProperty(notes = "회원 이름")
    @NotNull
    private String name;

    @ApiModelProperty(notes = "회원 이메일")
    @NotNull
    @Email
    private String email;

    @Builder
    public OAuth2UserInfoDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
