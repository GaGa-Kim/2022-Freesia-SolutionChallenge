package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FileService {
    FileResponseDto findByFileId(Long id);

    List<? extends File> imageList(Long id);

    List<FileIdResponseDto> findAll(Long id);

    @Transactional
    void delete(Long id);
}