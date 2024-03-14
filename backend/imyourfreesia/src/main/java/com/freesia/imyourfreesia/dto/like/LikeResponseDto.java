package com.freesia.imyourfreesia.dto.like;

import com.freesia.imyourfreesia.domain.like.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    @ApiModelProperty(notes = "좋아요 아이디", dataType = "Long", example = "1")
    private final Long likeId;

    @ApiModelProperty(notes = "좋아요 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    public LikeResponseDto(Like like) {
        this.likeId = like.getId();
        this.userId = like.getUser().getId();
        this.communityId = like.getCommunity().getId();
    }
}
