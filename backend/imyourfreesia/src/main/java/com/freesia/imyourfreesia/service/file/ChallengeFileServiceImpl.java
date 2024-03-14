package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileRepository;
import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeFileServiceImpl implements FileService {
    private final ChallengeFileRepository challengeFileRepository;
    private final ChallengeRepository challengeRepository;

    @Override
    public void saveFile(File file) {
        challengeFileRepository.save((ChallengeFile) file);
    }

    @Override
    public FileResponseDto getFileById(Long fileId) {
        ChallengeFile challengeFile = challengeFileRepository.findById(fileId).orElseThrow(NotFoundException::new);
        return new FileResponseDto(challengeFile);
    }

    @Override
    public List<ChallengeFile> findFileList(Long challengeId) {
        Challenge challenge = findChallengeById(challengeId);
        return challenge.getFiles();
    }

    @Override
    public List<FileIdResponseDto> getFileListByCommunityOrChallenge(Long challengeId) {
        Challenge challenge = findChallengeById(challengeId);
        return challenge.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        challengeFileRepository.deleteById(fileId);
    }

    private Challenge findChallengeById(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(NotFoundException::new);
    }
}