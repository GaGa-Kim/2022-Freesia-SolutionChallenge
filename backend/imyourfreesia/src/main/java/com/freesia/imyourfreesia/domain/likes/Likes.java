package com.freesia.imyourfreesia.domain.likes;

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

@Entity
@Getter
@NoArgsConstructor
public class Likes extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "pid")
    private Community community;

    @Builder
    public Likes(Long id, User user, Community community) {
        this.id = id;
        this.user = user;
        this.community = community;
    }
}
