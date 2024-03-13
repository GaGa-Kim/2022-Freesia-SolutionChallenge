package com.freesia.imyourfreesia.dto.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CommentListResponseDto {
    @ApiModelProperty(notes = "댓글 아이디", dataType = "Long", example = "1")
    private final Long commentId;

    @ApiModelProperty(notes = "댓글 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "댓글 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "댓글 작성 회원 닉네임", dataType = "String", example = "freesia")
    private final String nickName;

    @ApiModelProperty(notes = "댓글 작성 회원 프로필 사진", dataType = "String", example = "1234.png")
    private final String profileImg;

    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "댓글 생성 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "댓글 수정 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate modifiedDate;

    public CommentListResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.email = comment.getUser().getEmail();
        this.nickName = comment.getUser().getNickName();
        this.profileImg = comment.getUser().getProfileImg();
        this.communityId = comment.getCommunity().getId();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
