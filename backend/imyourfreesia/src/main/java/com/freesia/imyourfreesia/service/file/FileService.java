package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FileService {
    void saveFile(File file);

    FileResponseDto findByFileId(Long fileId);

    List<? extends File> fileList(Long id);

    List<FileIdResponseDto> findAllFileId(Long id);

    @Transactional
    void deleteFile(Long id);

    String getFileByteArray(Long id) throws IOException;
}