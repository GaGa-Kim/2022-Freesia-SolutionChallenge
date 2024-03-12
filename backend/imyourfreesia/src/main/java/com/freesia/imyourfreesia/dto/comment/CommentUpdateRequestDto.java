package com.freesia.imyourfreesia.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateRequestDto {
    @ApiModelProperty(notes = "커뮤니티 아이디")
    @NotNull
    private Long communityId;

    @ApiModelProperty(example = "커뮤니티 내용")
    @NotBlank
    private String content;

    @Builder
    public CommentUpdateRequestDto(Long communityId, String content) {
        this.communityId = communityId;
        this.content = content;
    }
}
