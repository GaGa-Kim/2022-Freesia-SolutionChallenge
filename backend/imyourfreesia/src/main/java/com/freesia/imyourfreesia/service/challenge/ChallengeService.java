package com.freesia.imyourfreesia.service.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFileRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import com.freesia.imyourfreesia.service.file.FileHandler;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeFileRepository challengeFileRepository;
    private final FileHandler fileHandler;
    private final UserService userService;

    /* 챌린지 등록 */
    @Transactional
    public Long save(ChallengeSaveRequestDto requestDto, List<MultipartFile> files) throws Exception {
        User user = userService.findUserById(requestDto.getUid());

        Challenge challenge = requestDto.toEntity();
        challenge.setUser(user);

        List<FileSaveRequestDto> fileList = fileHandler.saveFiles(files);
        if (!fileList.isEmpty()) {
            for (FileSaveRequestDto file : fileList) {
                challenge.addFile(challengeFileRepository.save(file.toChallengeFileEntity()));
            }
        }
        return challengeRepository.save(challenge).getId();
    }

    @Transactional(readOnly = true)
    public List<Challenge> findAllDesc() {
        return challengeRepository.findAllDesc();
    }

    /* 챌린지 상세 조회 */
    @Transactional(readOnly = true)
    public ChallengeResponseDto findById(Long id, List<Long> filePath) {
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new ChallengeResponseDto(challenge, filePath);
    }

    /* 챌린지 수정 - 사진 없을 때 */
    @Transactional
    public Challenge updateChallenge(Long id, ChallengeUpdateRequestDto requestDto) {
        Challenge challenge = challengeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return challenge.update(requestDto.getTitle(), requestDto.getContents());
    }

    /* 챌린지 수정 - 사진 있을 때 */
    @Transactional
    public Challenge updateChallengeImage(Long id, ChallengeUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Challenge challenge = challengeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        List<FileSaveRequestDto> fileList = fileHandler.saveFiles(files);
        if (!fileList.isEmpty()) {
            for (FileSaveRequestDto file : fileList) {
                challenge.addFile(challengeFileRepository.save(file.toChallengeFileEntity()));
            }
        }
        return challenge.update(requestDto.getTitle(), requestDto.getContents());
    }

    /* 챌린지 삭제 */
    @Transactional
    public void deleteChallenge(Long id) {
        Challenge challenge = challengeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        challengeRepository.delete(challenge);
    }

    /* 마이페이지 챌린지 조회 */
    @Transactional(readOnly = true)
    public List<ChallengeListResponseDto> findByUid(String email) {
        User user = userService.findUserByEmail(email);
        return challengeRepository.findByUser(user)
                .stream()
                .map(ChallengeListResponseDto::new)
                .collect(Collectors.toList());
    }
}
