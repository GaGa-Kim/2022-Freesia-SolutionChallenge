package com.freesia.imyourfreesia.domain.challenge;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChallengeTest {
    public static final Long CHALLENGE_ID = 1L;
    public static final String CHALLENGE_TITLE = "제목";
    public static final String NEW_CHALLENGE_TITLE = "새 제목";
    public static final String CHALLENGE_CONTENT = "내용";
    public static final String NEW_CHALLENGE_CONTENT = "새 내용";
    private Challenge challenge;

    public static Challenge testChallenge() {
        return Challenge.builder()
                .id(CHALLENGE_ID)
                .title(CHALLENGE_TITLE)
                .content(CHALLENGE_CONTENT)
                .build();
    }

    @BeforeEach
    void setUp() {
        challenge = testChallenge();
    }

    @Test
    @DisplayName("챌린지 추가 테스트")
    void testChallengeSave() {
        assertNotNull(challenge);
        assertEquals(CHALLENGE_ID, challenge.getId());
        assertEquals(CHALLENGE_TITLE, challenge.getTitle());
        assertEquals(CHALLENGE_CONTENT, challenge.getContent());
    }

    @Test
    @DisplayName("챌린지의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        challenge.setUser(user);

        assertEquals(user, challenge.getUser());
    }

    @Test
    @DisplayName("챌린지 수정 테스트")
    void testUpdate() {
        challenge.update(NEW_CHALLENGE_TITLE, NEW_CHALLENGE_CONTENT);

        assertEquals(NEW_CHALLENGE_TITLE, challenge.getTitle());
        assertEquals(NEW_CHALLENGE_CONTENT, challenge.getContent());
    }

    @Test
    @DisplayName("챌린지의 모든 파일 삭제 테스트")
    void testRemoveAllFiles() {
        ChallengeFile challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(challenge);

        assertEquals(1, challenge.getFiles().size());

        challenge.removeAllFiles();

        assertEquals(0, challenge.getFiles().size());
    }
}