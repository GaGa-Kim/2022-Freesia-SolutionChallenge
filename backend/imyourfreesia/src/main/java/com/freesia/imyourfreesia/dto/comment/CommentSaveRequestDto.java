package com.freesia.imyourfreesia.dto.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveRequestDto {
    @ApiModelProperty(notes = "댓글 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    @Email
    private String email;

    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    @NotNull
    private Long communityId;

    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    @NotEmpty
    private String content;

    @Builder
    public CommentSaveRequestDto(String email, Long communityId, String content) {
        this.email = email;
        this.communityId = communityId;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
