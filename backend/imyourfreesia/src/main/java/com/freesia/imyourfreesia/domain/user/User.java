package com.freesia.imyourfreesia.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseTimeEntity {
    @JsonIgnore
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "authorityId", referencedColumnName = "authorityId")})
    private Set<Authority> authorities;

    @Builder
    public User(String username, String loginId, String password, String email, String nickName, String profileImg, boolean activated,
                Set<Authority> authorities) {
        this.username = username;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.activated = activated;
        this.authorities = authorities;
    }

    public User update(String nickName, String profileImg) {
        this.nickName = nickName;
        this.profileImg = profileImg;
        return this;
    }

    public User pwUpdate(String password) {
        this.password = password;
        return this;
    }

}