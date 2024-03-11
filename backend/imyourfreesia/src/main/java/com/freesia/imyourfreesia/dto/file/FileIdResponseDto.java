package com.freesia.imyourfreesia.dto.file;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import lombok.Getter;

@Getter
public class FileIdResponseDto {
    private final Long fileId;

    public FileIdResponseDto(ChallengeFile challengeFile) {
        this.fileId = challengeFile.getId();
    }

    public FileIdResponseDto(CommunityFile communityFile) {
        this.fileId = communityFile.getId();
    }
}