package com.freesia.imyourfreesia.dto.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CommentListResponseDto {
    @ApiModelProperty(notes = "댓글 아이디")
    private final Long id;

    @ApiModelProperty(notes = "댓글 작성 회원 아이디")
    private final Long uid;

    @ApiModelProperty(notes = "댓글 작성 회원 이메일")
    private final String email;

    @ApiModelProperty(notes = "댓글 작성 회원 닉네임")
    private final String nickName;

    @ApiModelProperty(notes = "댓글 작성 회원 프로필 사진")
    private final String profileImg;

    @ApiModelProperty(notes = "커뮤니티 아이디")
    private final Long pid;

    @ApiModelProperty(notes = "댓글 내용")
    private final String content;

    @ApiModelProperty(notes = "댓글 생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "댓글 수정 날짜")
    private final LocalDate modifiedDate;

    public CommentListResponseDto(Comment comment) {
        this.id = comment.getId();
        this.uid = comment.getUser().getId();
        this.email = comment.getUser().getEmail();
        this.nickName = comment.getUser().getNickName();
        this.profileImg = comment.getUser().getProfileImg();
        this.pid = comment.getCommunity().getId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
