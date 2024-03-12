package com.freesia.imyourfreesia.dto.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveRequestDto {
    @ApiModelProperty(notes = "댓글 작성 회원 이메일")
    @NotNull
    private String email;

    @ApiModelProperty(notes = "커뮤니티 아이디")
    @NotNull
    private Long pid;

    @ApiModelProperty(example = "커뮤니티 내용")
    @NotBlank
    private String content;

    @Builder
    public CommentSaveRequestDto(String email, Long pid, String content) {
        this.email = email;
        this.pid = pid;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
