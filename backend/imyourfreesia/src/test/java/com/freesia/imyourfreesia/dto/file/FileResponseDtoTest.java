package com.freesia.imyourfreesia.dto.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileResponseDtoTest {
    private ChallengeFile challengeFile;
    private CommunityFile communityFile;

    public static FileResponseDto testChallengeFileResponseDto(ChallengeFile challengeFile) {
        return new FileResponseDto(challengeFile);
    }

    public static FileResponseDto testCommunityFileResponseDto(CommunityFile communityFile) {
        return new FileResponseDto(communityFile);
    }

    @BeforeEach
    void setUp() {
        challengeFile = ChallengeFileTest.testChallengeFile();
        communityFile = CommunityFileTest.testCommunityFile();
    }

    @Test
    @DisplayName("FileResponseDto 생성 테스트")
    void testFileResponseDtoSave() {
        FileResponseDto challengeFileResponseDto = testChallengeFileResponseDto(challengeFile);

        assertEquals(challengeFile.getOrigFileName(), challengeFileResponseDto.getOrigFileName());
        assertEquals(challengeFile.getFilePath(), challengeFileResponseDto.getFilePath());
        assertEquals(challengeFile.getFileSize(), challengeFileResponseDto.getFileSize());

        FileResponseDto communityFileResponseDto = testCommunityFileResponseDto(communityFile);

        assertEquals(communityFile.getOrigFileName(), communityFileResponseDto.getOrigFileName());
        assertEquals(communityFile.getFilePath(), communityFileResponseDto.getFilePath());
        assertEquals(communityFile.getFileSize(), communityFileResponseDto.getFileSize());
    }
}