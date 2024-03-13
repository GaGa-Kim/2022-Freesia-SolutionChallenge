package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Emoticon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emoticonId")
    @ApiModelProperty(notes = "이모티콘 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    @ApiModelProperty(notes = "이모티콘 작성 회원", dataType = "User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    @ApiModelProperty(notes = "챌린지", dataType = "Challenge")
    private Challenge challenge;

    @Column(nullable = false)
    @ApiModelProperty(notes = "이모티콘 이름", dataType = "String", example = "emoticion1")
    private String name;

    @Builder
    public Emoticon(Long id, User user, Challenge challenge, String name) {
        this.id = id;
        this.user = user;
        this.challenge = challenge;
        this.name = name;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
        if (!challenge.getEmoticons().contains(this)) {
            challenge.getEmoticons().add(this);
        }
    }
}