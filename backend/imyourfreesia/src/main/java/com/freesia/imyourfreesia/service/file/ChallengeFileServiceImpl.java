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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeFileServiceImpl implements FileService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeFileRepository challengeFileRepository;

    @Override
    public void saveFile(File file) {
        challengeFileRepository.save((ChallengeFile) file);
    }

    @Override
    public FileResponseDto findByFileId(Long fileId) {
        ChallengeFile challengeFile = challengeFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
        return new FileResponseDto(challengeFile);
    }

    @Override
    public List<ChallengeFile> fileList(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles();
    }

    @Override
    public List<FileIdResponseDto> findAllFileId(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        ChallengeFile challengeFile = challengeFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);
        challengeFileRepository.delete(challengeFile);
    }

    @Override
    public String getFileByteArray(Long fileId) throws IOException {
        FileResponseDto photoDto = findByFileId(fileId);
        InputStream imageStream = new FileInputStream(photoDto.getFilePath());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
}