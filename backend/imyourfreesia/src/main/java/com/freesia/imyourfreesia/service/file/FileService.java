package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface FileService {
    /**
     * 파일을 저장한다.
     *
     * @param file (저장할 파일)
     */
    void saveFile(File file);

    /**
     * 파일 아이디에 따른 파일을 조회한다.
     *
     * @param fileId (파일 아이디)
     * @return FileResponseDto (파일 정보를 담은 DTO)
     */
    FileResponseDto findByFileId(Long fileId);

    /**
     * 챌린지 아이디 또는 커뮤니티 아이디에 따른 파일 목록을 조회한다.
     *
     * @param id (챌린지 아이디 또는 커뮤니티 아이디)
     * @return List<? extends File> (파일 목록)
     */
    List<? extends File> fileList(Long id);

    /**
     * 챌린지 아이디 또는 커뮤니티 아이디에 따른 파일 아이디 목록을 조회한다.
     *
     * @param id (챌린지 아이디 또는 커뮤니티 아이디)
     * @return List<FileIdResponseDto> (파일 아이디 정보를 담은 DTO 목록)
     */
    List<FileIdResponseDto> findAllFileId(Long id);

    /**
     * 파일 아이디에 따른 파일을 삭제한다.
     *
     * @param fileId (파일 아이디)
     */
    @Transactional
    void deleteFile(Long fileId);

    /**
     * 파일을 ByteArray 형식으로 조회한다.
     *
     * @param fileId (파일 아이디)
     * @return String (파일의 ByteArray 형식)
     */
    String getFileByteArray(Long fileId) throws IOException;
}