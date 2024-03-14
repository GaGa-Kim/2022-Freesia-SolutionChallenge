package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2UserInfoRequestDto {
    @ApiModelProperty(notes = "회원 이름", dataType = "String", example = "freesia")
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    @Email
    private String email;

    @Builder
    public OAuth2UserInfoRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
