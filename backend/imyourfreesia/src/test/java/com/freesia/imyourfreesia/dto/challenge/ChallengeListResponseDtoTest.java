package com.freesia.imyourfreesia.dto.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChallengeListResponseDtoTest {
    private Challenge challenge;

    public static ChallengeListResponseDto testChallengeListResponseDto(Challenge challenge) {
        return new ChallengeListResponseDto(challenge);
    }

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(UserTest.testUser());
        ChallengeFile challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(challenge);
    }

    @Test
    @DisplayName("ChallengeListResponseDto 생성 테스트")
    void testChallengeListResponseDtoSave() {
        ChallengeListResponseDto challengeListResponseDto = testChallengeListResponseDto(challenge);

        assertEquals(challenge.getId(), challengeListResponseDto.getChallengeId());
        assertEquals(challenge.getUser().getId(), challengeListResponseDto.getUserId());
        assertEquals(challenge.getUser().getEmail(), challengeListResponseDto.getEmail());
        assertEquals(challenge.getUser().getNickName(), challengeListResponseDto.getNickName());
        assertEquals(challenge.getUser().getProfileImg(), challengeListResponseDto.getProfileImg());
        assertEquals(challenge.getTitle(), challengeListResponseDto.getTitle());
        assertEquals(challenge.getContent(), challengeListResponseDto.getContent());
        assertEquals(challenge.getFiles().get(0).getId(), challengeListResponseDto.getThumbnailFileId());
        assertEquals(challenge.getCreatedDate(), challengeListResponseDto.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), challengeListResponseDto.getModifiedDate());
    }
}