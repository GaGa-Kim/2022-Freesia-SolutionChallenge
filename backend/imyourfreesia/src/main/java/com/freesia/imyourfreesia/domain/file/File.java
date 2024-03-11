package com.freesia.imyourfreesia.domain.file;

import com.freesia.imyourfreesia.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class File extends BaseTimeEntity {
    @Column(nullable = false)
    private String origFileName;

    @Column(nullable = false)
    private String filePath;

    private Long fileSize;

    public File(String origFileName, String filePath, Long fileSize) {
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}