package com.freesia.imyourfreesia.dto.auth;

import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {
    @ApiModelProperty(notes = "회원 이름")
    @NotBlank
    private String username;

    @ApiModelProperty(notes = "회원 아이디")
    @NotBlank
    private String loginId;

    @ApiModelProperty(notes = "회원 비밀번호")
    @NotBlank
    @Size(min = 3, max = 100)
    private String password;

    @ApiModelProperty(notes = "회원 이메일")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(notes = "회원 닉네임")
    @NotNull
    @Size(min = 1, max = 100)
    private String nickName;

    @ApiModelProperty(notes = "회원 목표")
    @NotBlank
    @Size(min = 3, max = 100)
    private String goalMsg;

    @Builder
    public UserSaveRequestDto(GeneralAuthVO generalAuthVO) {
        this.username = generalAuthVO.getUsername();
        this.loginId = generalAuthVO.getLoginId();
        this.password = generalAuthVO.getPassword();
        this.email = generalAuthVO.getEmail();
        this.nickName = generalAuthVO.getNickName();
        this.goalMsg = generalAuthVO.getGoalMsg();
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .loginId(loginId)
                .email(email)
                .nickName(nickName)
                .build();
    }
}