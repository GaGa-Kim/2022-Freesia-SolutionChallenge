package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileRepository;
import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeFileServiceImpl implements FileService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeFileRepository challengeFileRepository;
    private final FileHandler fileHandler;

    public void saveFile(File file) {
        challengeFileRepository.save((ChallengeFile) file);
    }

    /* 이미지 개별 조회 */
    @Override
    public FileResponseDto findByFileId(Long fileId) {
        ChallengeFile challengeFile = challengeFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
        return new FileResponseDto(challengeFile);
    }

    /* 이미지 리스트 조회 */
    @Override
    public List<ChallengeFile> fileList(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles();
    }

    /* 이미지 아이디 리스트 조회 */
    @Override
    public List<FileIdResponseDto> findAllFileId(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    /* 이미지 삭제 */
    @Override
    public void deleteFile(Long id) {
        ChallengeFile challengeFile = challengeFileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        challengeFileRepository.delete(challengeFile);
    }

    /* 챌린지 이미지 ByteArray 조회 */
    @Override
    @Cacheable
    public String getFileByteArray(Long id) throws IOException {
        FileResponseDto photoDto = findByFileId(id);
        String absolutePath = fileHandler.findAbsoluteFilePath();
        String path = photoDto.getFilePath();
        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
}