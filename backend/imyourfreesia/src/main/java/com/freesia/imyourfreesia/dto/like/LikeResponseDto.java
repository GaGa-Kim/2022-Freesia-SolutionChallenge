package com.freesia.imyourfreesia.dto.like;

import com.freesia.imyourfreesia.domain.like.Like;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LikeResponseDto {
    @ApiModelProperty(notes = "좋아요 아이디")
    private final Long id;

    @ApiModelProperty(notes = "좋아요 작성 회원 아이디")
    private final Long uid;

    @ApiModelProperty(notes = "커뮤니티 아이디")
    private final Long pid;

    public LikeResponseDto(Like entity) {
        this.id = entity.getId();
        this.uid = entity.getUser().getId();
        this.pid = entity.getCommunity().getId();
    }
}
