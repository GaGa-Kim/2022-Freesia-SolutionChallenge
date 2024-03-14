package com.freesia.imyourfreesia.domain.file;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChallengeFileTest {
    public static final Long FILE_ID = 1L;
    public static final String FILE_ORIGINAL_NAME = "파일 이름";
    public static final String FILE_PATH = "/images/123.png";
    public static final Long FILE_SIZE = 123L;
    private ChallengeFile challengeFile;

    public static ChallengeFile testChallengeFile() {
        return ChallengeFile.builder()
                .id(FILE_ID)
                .origFileName(FILE_ORIGINAL_NAME)
                .filePath(FILE_PATH)
                .fileSize(FILE_SIZE)
                .build();
    }

    @BeforeEach
    void setUp() {
        challengeFile = testChallengeFile();
    }

    @Test
    @DisplayName("챌린지 파일 추가 테스트")
    void testChallengeFileSave() {
        assertNotNull(challengeFile);
        assertEquals(FILE_ORIGINAL_NAME, challengeFile.getOrigFileName());
        assertEquals(FILE_PATH, challengeFile.getFilePath());
        assertEquals(FILE_SIZE, challengeFile.getFileSize());
    }

    @Test
    @DisplayName("챌린지 파일의 챌린지 연관관계 설정 테스트")
    void testSetChallenge() {
        Challenge challenge = mock(Challenge.class);
        challengeFile.setChallenge(challenge);

        assertEquals(challenge, challengeFile.getChallenge());
    }
}