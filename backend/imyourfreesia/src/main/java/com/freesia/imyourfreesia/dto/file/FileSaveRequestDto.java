package com.freesia.imyourfreesia.dto.file;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileSaveRequestDto {
    @ApiModelProperty(example = "파일 원본 이름")
    private String origFileName;

    @ApiModelProperty(example = "파일 경로")
    private String filePath;

    @ApiModelProperty(example = "파일 크기")
    private Long fileSize;

    @Builder
    public FileSaveRequestDto(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public CommunityFile toCommunityFileEntity() {
        return CommunityFile.builder()
                .origFileName(origFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }

    public ChallengeFile toChallengeFileEntity() {
        return ChallengeFile.builder()
                .origFileName(origFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }
}
