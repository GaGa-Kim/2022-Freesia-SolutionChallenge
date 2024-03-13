package com.freesia.imyourfreesia.dto.auth;

import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {
    @ApiModelProperty(notes = "회원 이름", dataType = "String", example = "freesia")
    @NotEmpty
    private String username;

    @ApiModelProperty(notes = "회원 로그인 아이디", dataType = "String", example = "freesia123")
    @NotEmpty
    private String loginId;

    @ApiModelProperty(notes = "회원 비밀번호", dataType = "String", example = "password")
    @NotEmpty
    private String password;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    @Email
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "freesia")
    @NotEmpty
    private String nickName;

    @ApiModelProperty(notes = "회원 목표", dataType = "String", example = "꾸준히 노력하기")
    @NotEmpty
    private String goalMsg;

    @Builder
    public UserSaveRequestDto(UserRequestVO userRequestVO) {
        this.username = userRequestVO.getUsername();
        this.loginId = userRequestVO.getLoginId();
        this.password = userRequestVO.getPassword();
        this.email = userRequestVO.getEmail();
        this.nickName = userRequestVO.getNickName();
        this.goalMsg = userRequestVO.getGoalMsg();
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