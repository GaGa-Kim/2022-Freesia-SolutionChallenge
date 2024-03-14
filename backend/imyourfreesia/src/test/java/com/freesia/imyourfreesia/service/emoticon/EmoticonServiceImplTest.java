package com.freesia.imyourfreesia.service.emoticon;

import static com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDtoTest.ANOTHER_EMOTICON_COUNT;
import static com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDtoTest.EMOTICON1_COUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonRepository;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonTest;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonType;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDtoTest;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import com.freesia.imyourfreesia.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmoticonServiceImplTest {
    private Emoticon emoticon;

    @Mock
    private EmoticonRepository emoticonRepository;
    @Mock
    private UserService userService;
    @Mock
    private ChallengeService challengeService;
    @InjectMocks
    private EmoticonServiceImpl emoticonService;

    @BeforeEach
    void setUp() {
        emoticon = EmoticonTest.testEmoticon();
        emoticon.setUser(UserTest.testUser());
        emoticon.setChallenge(ChallengeTest.testChallenge());
    }

    @Test
    @DisplayName("이모티콘 저장 테스트")
    void testSaveEmotion() {
        when(userService.findUserByEmail(anyString())).thenReturn(emoticon.getUser());
        when(challengeService.findChallengeById(anyLong())).thenReturn(emoticon.getChallenge());
        when(emoticonRepository.save(any(Emoticon.class))).thenReturn(emoticon);
        when(emoticonRepository.countByChallengeAndUserAndName(any(), any(), eq(EmoticonType.EMOTICON1))).thenReturn(1L);

        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDtoTest.testEmoticonRequestDto(emoticon);
        EmoticonCountResponseDto result = emoticonService.saveEmotion(emoticonRequestDto);

        assertEquals(EMOTICON1_COUNT, result.getEmoticon1());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon2());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon3());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon4());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon5());

        verify(userService).findUserByEmail(anyString());
        verify(challengeService).findChallengeById(anyLong());
        verify(emoticonRepository).save(any(Emoticon.class));
        verify(emoticonRepository).countByChallengeAndUserAndName(any(), any(), eq(EmoticonType.EMOTICON1));
    }

    @Test
    @DisplayName("이모티콘 삭제 테스트")
    void testDeleteEmotion() {
        when(userService.findUserByEmail(anyString())).thenReturn(emoticon.getUser());
        when(challengeService.findChallengeById(anyLong())).thenReturn(emoticon.getChallenge());
        doNothing().when(emoticonRepository).deleteByUserAndChallengeAndName(any(), any(), any());

        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDtoTest.testEmoticonRequestDto(emoticon);
        EmoticonCountResponseDto result = emoticonService.deleteEmotion(emoticonRequestDto);

        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon1());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon2());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon3());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon4());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon5());

        verify(userService).findUserByEmail(anyString());
        verify(challengeService).findChallengeById(anyLong());
        verify(emoticonRepository).deleteByUserAndChallengeAndName(any(), any(), any());
    }

    @Test
    @DisplayName("챌린지와 회원에 따른 각 이모티콘 개수 조회 테스트")
    void testGetEmoticonByChallengeAndUser() {
        when(challengeService.findChallengeById(anyLong())).thenReturn(emoticon.getChallenge());
        when(userService.findUserByEmail(anyString())).thenReturn(emoticon.getUser());
        when(emoticonRepository.countByChallengeAndUserAndName(any(), any(), eq(EmoticonType.EMOTICON1))).thenReturn(1L);

        EmoticonCountResponseDto result = emoticonService.getEmoticonByChallengeAndUser(emoticon.getId(), emoticon.getUser().getEmail());

        assertEquals(EMOTICON1_COUNT, result.getEmoticon1());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon2());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon3());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon4());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon5());

        verify(userService).findUserByEmail(anyString());
        verify(challengeService).findChallengeById(anyLong());
        verify(emoticonRepository).countByChallengeAndUserAndName(any(), any(), eq(EmoticonType.EMOTICON1));
    }

    @Test
    @DisplayName("챌린지의 각 이모티콘 개수 조회 테스트")
    void testCountByChallenge() {
        when(challengeService.findChallengeById(anyLong())).thenReturn(emoticon.getChallenge());
        when(emoticonRepository.countByChallengeAndName(any(), eq(EmoticonType.EMOTICON1))).thenReturn(1L);

        EmoticonCountResponseDto result = emoticonService.countByChallenge(emoticon.getChallenge().getId());

        assertEquals(EMOTICON1_COUNT, result.getEmoticon1());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon2());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon3());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon4());
        assertEquals(ANOTHER_EMOTICON_COUNT, result.getEmoticon5());

        verify(challengeService).findChallengeById(anyLong());
        verify(emoticonRepository).countByChallengeAndName(any(), eq(EmoticonType.EMOTICON1));
    }
}