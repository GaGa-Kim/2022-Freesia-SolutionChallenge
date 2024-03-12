package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
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
    @Column(name = "emotionId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challenge;

    @Column(nullable = false)
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