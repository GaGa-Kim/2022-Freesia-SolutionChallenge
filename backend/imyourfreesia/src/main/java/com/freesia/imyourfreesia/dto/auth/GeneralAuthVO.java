package com.freesia.imyourfreesia.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class GeneralAuthVO {
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
    @NotBlank
    @Size(min = 1, max = 100)
    private String nickName;

    @ApiModelProperty(notes = "회원 프로필 이미지")
    @NotNull
    private MultipartFile profileImg;

    @ApiModelProperty(notes = "회원 목표")
    @NotNull
    @Size(min = 3, max = 100)
    private String goalMsg;
}