package com.freesia.imyourfreesia.domain.emoticon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmoticonRepositoryTest {
    private Emoticon emoticon;

    @Autowired
    private EmoticonRepository emoticonRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        emoticon = EmoticonTest.testEmoticon();
        emoticon.setUser(userRepository.save(UserTest.testUser()));
        emoticon.setChallenge(challengeRepository.save(ChallengeTest.testChallenge()));
        emoticon = emoticonRepository.save(emoticon);
    }

    @AfterEach
    void clean() {
        emoticonRepository.deleteAll();
        challengeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("챌린지, 회원, 이모티콘 이름에 따른 이모티콘 개수 조회 테스트")
    void countByChallengeAndUserAndName() {
        Long foundEmotion = emoticonRepository.countByChallengeAndUserAndName(emoticon.getChallenge(), emoticon.getUser(), emoticon.getName());

        assertEquals(1, foundEmotion);
    }

    @Test
    @DisplayName("챌린지, 이모티콘 이름에 따른 이모티콘 개수 조회 테스트")
    void countByChallengeAndName() {
        Long foundEmotion = emoticonRepository.countByChallengeAndName(emoticon.getChallenge(), emoticon.getName());

        assertEquals(1, foundEmotion);
    }

    @Test
    @DisplayName("회원, 챌린지, 이모티콘 이름에 따른 이모티콘 삭제 테스트")
    void deleteByUserAndChallengeAndName() {
        emoticonRepository.deleteByUserAndChallengeAndName(emoticon.getUser(), emoticon.getChallenge(), emoticon.getName());
        Long foundEmotion = emoticonRepository.countByChallengeAndUserAndName(emoticon.getChallenge(), emoticon.getUser(), emoticon.getName());

        assertEquals(0, foundEmotion);
    }
}