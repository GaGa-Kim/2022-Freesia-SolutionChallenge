package com.freesia.imyourfreesia.domain.like;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import com.freesia.imyourfreesia.domain.community.Community;
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

@Entity(name = "Likes")
@Getter
@NoArgsConstructor
public class Like extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    @ApiModelProperty(notes = "좋아요 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    @ApiModelProperty(notes = "좋아요 작성 회원", dataType = "User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "communityId")
    @ApiModelProperty(notes = "커뮤니티", dataType = "Community")
    private Community community;

    @Builder
    public Like(Long id) {
        this.id = id;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getLikes().contains(this)) {
            community.getLikes().add(this);
        }
    }
}
