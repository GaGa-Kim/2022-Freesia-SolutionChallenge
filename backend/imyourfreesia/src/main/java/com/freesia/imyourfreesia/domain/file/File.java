package com.freesia.imyourfreesia.domain.file;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class File extends BaseTimeEntity {
    @Column(nullable = false)
    @ApiModelProperty(notes = "파일 원본 이름", dataType = "String", example = "freesia")
    private String origFileName;

    @Column(nullable = false)
    @ApiModelProperty(notes = "파일 경로", dataType = "String", example = "/images/")
    private String filePath;

    @ApiModelProperty(notes = "파일 크기", dataType = "Long", example = "15")
    private Long fileSize;

    public File(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}