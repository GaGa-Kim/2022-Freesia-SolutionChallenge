package com.freesia.imyourfreesia.dto.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChallengeResponseDtoTest {
    private Challenge challenge;
    private ChallengeFile challengeFile;

    public static ChallengeResponseDto testChallengeResponseDto(Challenge challenge, ChallengeFile challengeFile) {
        return new ChallengeResponseDto(challenge, Collections.singletonList(challengeFile.getId()));
    }

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(UserTest.testUser());
        challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(challenge);
    }

    @Test
    @DisplayName("ChallengeResponseDto 생성 테스트")
    void testChallengeResponseDtoSave() {
        ChallengeResponseDto challengeResponseDto = testChallengeResponseDto(challenge, challengeFile);

        assertEquals(challenge.getId(), challengeResponseDto.getChallengeId());
        assertEquals(challenge.getUser().getId(), challengeResponseDto.getUserId());
        assertEquals(challenge.getUser().getEmail(), challengeResponseDto.getEmail());
        assertEquals(challenge.getUser().getNickName(), challengeResponseDto.getNickName());
        assertEquals(challenge.getTitle(), challengeResponseDto.getTitle());
        assertEquals(challenge.getContent(), challengeResponseDto.getContent());
        assertEquals(challenge.getFiles().get(0).getId(), challengeResponseDto.getFileIdList().get(0));
        assertEquals(challenge.getCreatedDate(), challengeResponseDto.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), challengeResponseDto.getModifiedDate());
    }
}