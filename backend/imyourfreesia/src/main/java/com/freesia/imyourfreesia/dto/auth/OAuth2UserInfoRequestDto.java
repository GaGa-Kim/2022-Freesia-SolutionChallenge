package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserInfoRequestDto {
    @ApiModelProperty(notes = "회원 이름")
    @NotBlank
    private String name;

    @ApiModelProperty(notes = "회원 이메일")
    @NotNull
    @NotBlank
    private String email;

    @Builder
    public OAuth2UserInfoRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
