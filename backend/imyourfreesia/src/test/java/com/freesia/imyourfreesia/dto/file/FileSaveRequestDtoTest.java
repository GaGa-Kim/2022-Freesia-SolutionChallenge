package com.freesia.imyourfreesia.dto.file;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileSaveRequestDtoTest {
    private final ValidatorUtil<FileSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private ChallengeFile challengeFile;
    private CommunityFile communityFile;

    public static FileSaveRequestDto testChallengeFileSaveRequestDto(ChallengeFile challengeFile) {
        return FileSaveRequestDto.builder()
                .origFileName(challengeFile.getOrigFileName())
                .filePath(challengeFile.getFilePath())
                .fileSize(challengeFile.getFileSize())
                .build();
    }

    public static FileSaveRequestDto testCommunityFileSaveRequestDto(CommunityFile communityFile) {
        return FileSaveRequestDto.builder()
                .origFileName(communityFile.getOrigFileName())
                .filePath(communityFile.getFilePath())
                .fileSize(communityFile.getFileSize())
                .build();
    }

    @BeforeEach
    void setUp() {
        challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(ChallengeTest.testChallenge());
        communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("FileSaveRequestDto 생성 테스트")
    void testFileSaveRequestDtoSave() {
        FileSaveRequestDto challengeFileSaveRequestDto = testChallengeFileSaveRequestDto(challengeFile);
        assertEquals(challengeFile.getOrigFileName(), challengeFileSaveRequestDto.getOrigFileName());
        assertEquals(challengeFile.getFilePath(), challengeFileSaveRequestDto.getFilePath());
        assertEquals(challengeFile.getFileSize(), challengeFileSaveRequestDto.getFileSize());

        FileSaveRequestDto communityFileSaveRequestDto = testChallengeFileSaveRequestDto(challengeFile);

        assertEquals(communityFile.getOrigFileName(), communityFileSaveRequestDto.getOrigFileName());
        assertEquals(communityFile.getFilePath(), communityFileSaveRequestDto.getFilePath());
        assertEquals(communityFile.getFileSize(), communityFileSaveRequestDto.getFileSize());
    }

    @Test
    @DisplayName("FileSaveRequestDto toEntity 생성 테스트")
    void testFileSaveRequestDtoEntity() {
        FileSaveRequestDto challengeFileSaveRequestDto = testChallengeFileSaveRequestDto(challengeFile);

        ChallengeFile challengeFileEntity = challengeFileSaveRequestDto.toChallengeFileEntity();
        challengeFileEntity.setChallenge(challengeFile.getChallenge());

        assertNotNull(challengeFileEntity);
        assertEquals(challengeFileSaveRequestDto.getOrigFileName(), challengeFileEntity.getOrigFileName());
        assertEquals(challengeFileSaveRequestDto.getFilePath(), challengeFileEntity.getFilePath());
        assertEquals(challengeFileSaveRequestDto.getFileSize(), challengeFileEntity.getFileSize());

        FileSaveRequestDto communityFileSaveRequestDto = testCommunityFileSaveRequestDto(communityFile);

        CommunityFile communityFileEntity = communityFileSaveRequestDto.toCommunityFileEntity();
        communityFileEntity.setCommunity(communityFile.getCommunity());

        assertNotNull(communityFileEntity);
        assertEquals(communityFileSaveRequestDto.getOrigFileName(), communityFileEntity.getOrigFileName());
        assertEquals(communityFileSaveRequestDto.getFilePath(), communityFileEntity.getFilePath());
        assertEquals(communityFileSaveRequestDto.getFileSize(), communityFileEntity.getFileSize());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        FileSaveRequestDto fileSaveRequestDto = new FileSaveRequestDto();

        assertNotNull(fileSaveRequestDto);
        assertNull(fileSaveRequestDto.getOrigFileName());
        assertNull(fileSaveRequestDto.getFilePath());
        assertNull(fileSaveRequestDto.getFileSize());
    }

    @Test
    @DisplayName("파일 원본 이름 유효성 검증 테스트")
    void origFileName_validation() {
        FileSaveRequestDto fileSaveRequestDto = FileSaveRequestDto.builder()
                .origFileName(INVALID_EMPTY)
                .filePath(challengeFile.getFilePath())
                .fileSize(challengeFile.getFileSize())
                .build();

        validatorUtil.validate(fileSaveRequestDto);
    }

    @Test
    @DisplayName("파일 경로 유효성 검증 테스트")
    void filePath_validation() {
        FileSaveRequestDto fileSaveRequestDto = FileSaveRequestDto.builder()
                .origFileName(challengeFile.getOrigFileName())
                .filePath(INVALID_EMPTY)
                .fileSize(challengeFile.getFileSize())
                .build();

        validatorUtil.validate(fileSaveRequestDto);
    }
}