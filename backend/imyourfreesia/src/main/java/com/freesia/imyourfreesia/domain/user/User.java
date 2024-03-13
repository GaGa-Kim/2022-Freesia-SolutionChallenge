package com.freesia.imyourfreesia.domain.user;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private Long id;

    @ApiModelProperty(notes = "회원 이름", dataType = "String", example = "freesia")
    private String username;

    @Column(unique = true)
    @ApiModelProperty(notes = "회원 로그인 아이디", dataType = "String", example = "freesia123")
    private String loginId;

    private String password;

    @Column(unique = true)
    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "freesia")
    private String nickName;

    @ApiModelProperty(notes = "회원 프로필 이미지", dataType = "String", example = "/images/")
    private String profileImg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean activated;

    @Builder
    public User(Long id, String username, String loginId, String password, String email, String nickName, String profileImg) {
        this.id = id;
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.role = Role.USER;
        this.activated = true;
    }

    public void updateProfile(String nickName, String profileImg) {
        this.nickName = nickName;
        this.profileImg = profileImg;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImg(String profilePath) {
        this.profileImg = profilePath;
    }
}