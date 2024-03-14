package com.freesia.imyourfreesia.dto.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileIdResponseDtoTest {
    private ChallengeFile challengeFile;
    private CommunityFile communityFile;

    public static FileIdResponseDto testChallengeFileIdResponseDto(ChallengeFile challengeFile) {
        return new FileIdResponseDto(challengeFile);
    }

    public static FileIdResponseDto testCommunityFileIdResponseDto(CommunityFile communityFile) {
        return new FileIdResponseDto(communityFile);
    }

    @BeforeEach
    void setUp() {
        challengeFile = ChallengeFileTest.testChallengeFile();
        communityFile = CommunityFileTest.testCommunityFile();
    }

    @Test
    @DisplayName("FileIdResponseDto 생성 테스트")
    void testFileIdResponseDtoSave() {
        FileIdResponseDto challengeFileIdResponseDto = testChallengeFileIdResponseDto(challengeFile);

        assertEquals(challengeFile.getId(), challengeFileIdResponseDto.getFileId());

        FileIdResponseDto communityFileIdResponseDto = testCommunityFileIdResponseDto(communityFile);

        assertEquals(communityFile.getId(), communityFileIdResponseDto.getFileId());
    }
}