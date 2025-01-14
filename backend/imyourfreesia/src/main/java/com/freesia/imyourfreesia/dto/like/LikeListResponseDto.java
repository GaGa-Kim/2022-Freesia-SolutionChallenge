package com.freesia.imyourfreesia.dto.like;

import com.freesia.imyourfreesia.domain.like.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LikeListResponseDto {
    @ApiModelProperty(notes = "좋아요 아이디", dataType = "Long", example = "1")
    private final Long likeId;

    @ApiModelProperty(notes = "좋아요 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    @ApiModelProperty(notes = "커뮤니티 제목", dataType = "String", example = "제목")
    private final String title;

    @ApiModelProperty(notes = "커뮤니티 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "커뮤니티 카테고리", dataType = "String", example = "worries")
    private final String category;

    public LikeListResponseDto(Like like) {
        this.likeId = like.getId();
        this.userId = like.getUser().getId();
        this.communityId = like.getCommunity().getId();
        this.title = like.getCommunity().getTitle();
        this.content = like.getCommunity().getContent();
        this.category = like.getCommunity().getCategory().getCategoryName();
    }
}
