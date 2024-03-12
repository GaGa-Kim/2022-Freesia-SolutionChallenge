package com.freesia.imyourfreesia.dto.like;

import com.freesia.imyourfreesia.domain.like.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LikeListResponseDto {
    @ApiModelProperty(notes = "좋아요 아이디")
    private final Long id;

    @ApiModelProperty(notes = "좋아요 작성 회원 아이디")
    private final Long uid;

    @ApiModelProperty(notes = "커뮤니티 아이디")
    private final Long pid;

    @ApiModelProperty(notes = "커뮤니티 제목")
    private final String ptitle;

    @ApiModelProperty(notes = "커뮤니티 내용")
    private final String pcontent;

    @ApiModelProperty(notes = "커뮤니티 카테고리")
    private final String pcategory;

    public LikeListResponseDto(Like entity) {
        this.id = entity.getId();
        this.uid = entity.getUser().getId();
        this.pid = entity.getCommunity().getId();
        this.ptitle = entity.getCommunity().getTitle();
        this.pcontent = entity.getCommunity().getContent();
        this.pcategory = entity.getCommunity().getCategory();
    }
}
