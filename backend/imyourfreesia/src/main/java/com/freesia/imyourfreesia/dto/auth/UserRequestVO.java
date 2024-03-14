package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequestVO {
    @ApiModelProperty(notes = "회원 이름", dataType = "String", example = "freesia")
    private String username;

    @ApiModelProperty(notes = "회원 로그인 아이디", dataType = "String", example = "freesia123")
    private String loginId;

    @ApiModelProperty(notes = "회원 비밀번호", dataType = "String", example = "password")
    private String password;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "freesia")
    private String nickName;

    @ApiModelProperty(notes = "회원 프로필 이미지")
    private MultipartFile profileImg;

    @ApiModelProperty(notes = "회원 목표", dataType = "String", example = "꾸준히 노력하기")
    private String goalMsg;

    @Builder
    public UserRequestVO(String username, String loginId, String password, String email, String nickName, MultipartFile profileImg, String goalMsg) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.goalMsg = goalMsg;
    }
}