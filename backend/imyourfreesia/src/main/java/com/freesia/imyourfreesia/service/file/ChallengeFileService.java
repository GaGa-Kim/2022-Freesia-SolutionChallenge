package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileRepository;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeFileService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeFileRepository challengeFileRepository;

    /* 이미지 개별 조회 */
    @Transactional(readOnly = true)
    public FileResponseDto findByImageId(Long id) {
        ChallengeFile challengeFile = challengeFileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new FileResponseDto(challengeFile);
    }

    /* 이미지 리스트 조회 */
    @Transactional(readOnly = true)
    public List<ChallengeFile> imageList(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles();
    }

    /* 이미지 아이디 리스트 조회 */
    @Transactional(readOnly = true)
    public List<FileIdResponseDto> findAllByChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.getById(challengeId);
        return challenge.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }
    
    /* 이미지 삭제 */
    @Transactional
    public void deletePhoto(Long id) {
        ChallengeFile challengeFile = challengeFileRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        challengeFileRepository.delete(challengeFile);
    }
}