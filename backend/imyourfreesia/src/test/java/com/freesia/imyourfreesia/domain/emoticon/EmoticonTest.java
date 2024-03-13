package com.freesia.imyourfreesia.domain.emoticon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmoticonTest {
    public static final Long EMOTICON_ID = 1L;
    public static final String EMOTICON_NAME = EmoticonType.EMOTICON1.getEmoticonName();
    private Emoticon emoticon;

    public static Emoticon testEmoticon() {
        return Emoticon.builder()
                .id(EMOTICON_ID)
                .name(EMOTICON_NAME)
                .build();
    }

    @BeforeEach
    void setUp() {
        emoticon = testEmoticon();
    }

    @Test
    @DisplayName("이모티콘 추가 테스트")
    void testEmoticonSave() {
        assertNotNull(emoticon);
        assertEquals(EMOTICON_ID, emoticon.getId());
        assertEquals(EMOTICON_NAME, emoticon.getName().getEmoticonName());
    }

    @Test
    @DisplayName("이모티콘의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        emoticon.setUser(user);

        assertEquals(user, emoticon.getUser());
    }

    @Test
    @DisplayName("이모티콘의 챌린지 연관관계 설정 테스트")
    void testSetCommunity() {
        Challenge challenge = mock(Challenge.class);
        emoticon.setChallenge(challenge);

        assertEquals(challenge, emoticon.getChallenge());
    }
}