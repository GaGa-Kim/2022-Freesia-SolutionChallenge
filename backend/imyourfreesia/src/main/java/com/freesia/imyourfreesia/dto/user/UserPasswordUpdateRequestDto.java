package com.freesia.imyourfreesia.dto.user;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateRequestDto {
    @ApiModelProperty(notes = "회원 비밀번호", dataType = "String", example = "password")
    @NotEmpty
    @Size(min = 3, max = 100)
    private String password;

    @Builder
    public UserPasswordUpdateRequestDto(String password) {
        this.password = password;
    }
}
