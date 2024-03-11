package com.freesia.imyourfreesia.domain.user;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
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

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true)
    private String loginId;

    private String password;

    @Column(unique = true)
    private String email;

    private String nickName;

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

    public User update(String nickName, String profileImg) {
        this.nickName = nickName;
        this.profileImg = profileImg;
        return this;
    }

    public void pwUpdate(String password) {
        this.password = password;
    }
}