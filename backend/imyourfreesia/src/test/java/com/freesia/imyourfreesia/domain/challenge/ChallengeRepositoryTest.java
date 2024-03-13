package com.freesia.imyourfreesia.domain.challenge;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.domain.user.UserTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChallengeRepositoryTest {
    private Challenge challenge;

    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(userRepository.save(UserTest.testUser()));
        challenge = challengeRepository.save(challenge);
    }

    @AfterEach
    void clean() {
        challengeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("전체 챌린지 목록 조회 테스트")
    void testFindAllDesc() {
        List<Challenge> foundChallengeList = challengeRepository.findAllDesc();

        assertNotNull(foundChallengeList);
        assertEquals(1, foundChallengeList.size());
    }

    @Test
    @DisplayName("회원에 따른 챌린지 목록 조회 테스트")
    void testFindByUser() {
        List<Challenge> foundChallengeList = challengeRepository.findByUser(challenge.getUser());

        assertNotNull(foundChallengeList);
        assertEquals(1, foundChallengeList.size());
        assertEquals(challenge.getUser().getId(), foundChallengeList.get(0).getUser().getId());
    }
}