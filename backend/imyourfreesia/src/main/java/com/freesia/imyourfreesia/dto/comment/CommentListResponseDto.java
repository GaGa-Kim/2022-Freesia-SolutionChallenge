package com.freesia.imyourfreesia.dto.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CommentListResponseDto {
    private Long id;

    //private User uid;
    private Long uid;
    private String username;
    private String loginId;
    private String email;
    private String nickName;
    private String profileImg;

    private Long pid;
    private String content;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public CommentListResponseDto(Comment entity) {
        this.id = entity.getId();

        this.uid = entity.getUser().getId();
        this.username = entity.getUser().getUsername();
        this.loginId = entity.getUser().getLoginId();
        this.email = entity.getUser().getEmail();
        this.nickName = entity.getUser().getNickName();
        this.profileImg = entity.getUser().getProfileImg();

        this.pid = entity.getCommunity().getId();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
