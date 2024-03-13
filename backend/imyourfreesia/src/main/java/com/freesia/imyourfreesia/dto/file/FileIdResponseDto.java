package com.freesia.imyourfreesia.dto.file;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class FileIdResponseDto {
    @ApiModelProperty(notes = "파일 아이디", dataType = "Long", example = "1")
    private final Long fileId;

    public FileIdResponseDto(ChallengeFile challengeFile) {
        this.fileId = challengeFile.getId();
    }

    public FileIdResponseDto(CommunityFile communityFile) {
        this.fileId = communityFile.getId();
    }
}