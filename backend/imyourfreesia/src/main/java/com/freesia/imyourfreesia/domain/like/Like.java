package com.freesia.imyourfreesia.domain.like;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.community.Community;
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

@Entity(name = "Likes")
@Getter
@NoArgsConstructor
public class Like extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pid")
    private Community community;

    @Builder
    public Like(Long id, User user, Community community) {
        this.id = id;
        this.user = user;
        this.community = community;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getLikes().contains(this)) {
            community.getLikes().add(this);
        }
    }
}
