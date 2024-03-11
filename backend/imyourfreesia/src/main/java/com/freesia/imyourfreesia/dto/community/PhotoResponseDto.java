package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.CommunityPhoto;
import lombok.Getter;

@Getter
public class PhotoResponseDto {
    private Long fileId;

    public PhotoResponseDto(CommunityPhoto communityPhoto) {
        this.fileId = communityPhoto.getId();
    }
}