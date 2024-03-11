package com.freesia.imyourfreesia.dto.file;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class FileResponseDto {
    @ApiModelProperty(example = "파일 원본 이름")
    private String origFileName;

    @ApiModelProperty(example = "파일 경로")
    private String filePath;

    @ApiModelProperty(example = "파일 크기")
    private Long fileSize;

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