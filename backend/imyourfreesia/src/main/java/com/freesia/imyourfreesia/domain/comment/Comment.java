package com.freesia.imyourfreesia.domain.comment;

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

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    @ApiModelProperty(notes = "댓글 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "userId")
    @ApiModelProperty(notes = "댓글 작성 회원", dataType = "User")
    private User user;

    @ManyToOne
    @JoinColumn(name = "communityId")
    @ApiModelProperty(notes = "커뮤니티", dataType = "Community")
    private Community community;

    @Column(columnDefinition = "TEXT", nullable = false)
    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    private String content;

    @Builder
    public Comment(Long id, Community cid, String content) {
        this.id = id;
        this.community = cid;
        this.content = content;
    }

    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getComments().contains(this)) {
            community.getComments().add(this);
        }
    }
}
