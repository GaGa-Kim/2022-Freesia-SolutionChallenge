package com.freesia.imyourfreesia.service.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.file.FileHandler;
import com.freesia.imyourfreesia.service.file.FileService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final FileService challengeFileServiceImpl;

    private final UserService userService;
    private final FileHandler fileHandler;

    @Override
    public ChallengeResponseDto saveChallenge(ChallengeSaveRequestDto requestDto, List<MultipartFile> files) throws Exception {
        User user = userService.findUserById(requestDto.getUid());
        Challenge challenge = requestDto.toEntity();
        challenge.setUser(user);
        saveChallengeFiles(challenge, files);
        challengeRepository.save(challenge);
        return new ChallengeResponseDto(challenge, getFileIdListByChallenge(challenge));
    }

    @Override
    public List<ChallengeListResponseDto> findAllChallengeDesc() {
        return challengeRepository.findAllDesc()
                .stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public ChallengeResponseDto findChallengeById(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(NotFoundException::new);
        return new ChallengeResponseDto(challenge, getFileIdListByChallenge(challenge));
    }

    @Override
    public ChallengeResponseDto updateChallenge(Long challengeId, ChallengeUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(NotFoundException::new);
        if (!files.isEmpty()) {
            challenge.removeAllFiles();
            saveChallengeFiles(challenge, files);
        }
        challenge.update(requestDto.getTitle(), requestDto.getContents());
        return new ChallengeResponseDto(challenge, getFileIdListByChallenge(challenge));
    }

    @Override
    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }

    @Override
    public List<ChallengeListResponseDto> findChallengeByUser(String email) {
        User user = userService.findUserByEmail(email);
        return challengeRepository.findByUser(user)
                .stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    private void saveChallengeFiles(Challenge challenge, List<MultipartFile> files) throws IOException {
        List<FileSaveRequestDto> savedFiles = fileHandler.saveFiles(files);
        for (FileSaveRequestDto savedFile : savedFiles) {
            ChallengeFile challengeFile = savedFile.toChallengeFileEntity();
            challenge.addFile(challengeFile);
            challengeFileServiceImpl.saveFile(challengeFile);
        }
    }

    private List<Long> getFileIdListByChallenge(Challenge challenge) {
        return challenge.getFiles().stream()
                .map(ChallengeFile::getId)
                .collect(Collectors.toList());
    }
}