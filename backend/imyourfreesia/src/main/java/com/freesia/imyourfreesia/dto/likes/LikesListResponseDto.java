package com.freesia.imyourfreesia.dto.likes;

import com.freesia.imyourfreesia.domain.likes.Likes;
import lombok.Getter;

@Getter
public class LikesListResponseDto {
    private Long id;
    private Long uid;

    private Long pid;
    private String ptitle;
    private String pcontent;
    private String pcategory;

    public LikesListResponseDto(Likes entity) {
        this.id = entity.getId();
        this.uid = entity.getUser().getId();
        this.pid = entity.getCommunity().getId();
        this.ptitle = entity.getCommunity().getTitle();
        this.pcontent = entity.getCommunity().getContent();
        this.pcategory = entity.getCommunity().getCategory();
    }
}
