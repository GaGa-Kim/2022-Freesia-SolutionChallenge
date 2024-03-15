package com.freesia.imyourfreesia.service.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.file.FileService;
import com.freesia.imyourfreesia.service.user.UserService;
import com.freesia.imyourfreesia.util.FileHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
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
        User user = userService.findUserById(requestDto.getUserId());
        Challenge challenge = requestDto.toEntity();
        challenge.setUser(user);
        saveChallengeFiles(challenge, files);
        challengeRepository.save(challenge);
        return new ChallengeResponseDto(challenge, findFileIdListByChallenge(challenge));
    }

    @Override
    public List<ChallengeListResponseDto> getAllChallengeList() {
        return challengeRepository.findAllDesc()
                .stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Challenge findChallengeById(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(NotFoundException::new);
    }

    @Override
    public ChallengeResponseDto getChallengeById(Long challengeId) {
        Challenge challenge = findChallengeById(challengeId);
        return new ChallengeResponseDto(challenge, findFileIdListByChallenge(challenge));
    }

    @Override
    public ChallengeResponseDto updateChallenge(Long challengeId, ChallengeUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Challenge challenge = findChallengeById(challengeId);
        if (!files.isEmpty()) {
            challenge.removeAllFiles();
            saveChallengeFiles(challenge, files);
        }
        challenge.update(requestDto.getTitle(), requestDto.getContent());
        return new ChallengeResponseDto(challenge, findFileIdListByChallenge(challenge));
    }

    @Override
    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }

    @Override
    public List<ChallengeListResponseDto> getChallengeListByUser(String email) {
        User user = userService.findUserByEmail(email);
        return challengeRepository.findByUser(user)
                .stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getFileByteArray(Long fileId) throws IOException {
        FileResponseDto photoDto = challengeFileServiceImpl.getFileById(fileId);
        InputStream imageStream = new FileInputStream(photoDto.getFilePath());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    private void saveChallengeFiles(Challenge challenge, List<MultipartFile> files) throws IOException {
        List<FileSaveRequestDto> savedFiles = fileHandler.saveFiles(files);
        for (FileSaveRequestDto savedFile : savedFiles) {
            ChallengeFile challengeFile = savedFile.toChallengeFileEntity();
            challengeFile.setChallenge(challenge);
            challengeFileServiceImpl.saveFile(challengeFile);
        }
    }

    private List<Long> findFileIdListByChallenge(Challenge challenge) {
        return challenge.getFiles()
                .stream()
                .map(ChallengeFile::getId)
                .collect(Collectors.toList());
    }
}