package com.freesia.imyourfreesia.dto.file;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class FileResponseDto {
    @ApiModelProperty(notes = "파일 원본 이름", dataType = "String", example = "freesia")
    private final String origFileName;

    @ApiModelProperty(notes = "파일 경로", dataType = "String", example = "/images/")
    private final String filePath;

    @ApiModelProperty(notes = "파일 크기", dataType = "Long", example = "15")
    private final Long fileSize;

    public FileResponseDto(ChallengeFile challengeFile) {
        this.origFileName = challengeFile.getOrigFileName();
        this.filePath = challengeFile.getFilePath();
        this.fileSize = challengeFile.getFileSize();
    }

    public FileResponseDto(CommunityFile communityFile) {
        this.origFileName = communityFile.getOrigFileName();
        this.filePath = communityFile.getFilePath();
        this.fileSize = communityFile.getFileSize();
    }
}