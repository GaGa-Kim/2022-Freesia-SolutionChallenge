package com.freesia.imyourfreesia.dto.emoticon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmoticonCountResponseDtoTest {
    public static final Long EMOTICON1_COUNT = 1L;
    public static final Long ANOTHER_EMOTICON_COUNT = 0L;

    public static EmoticonCountResponseDto testEmoticonCountResponseDto() {
        return new EmoticonCountResponseDto(EMOTICON1_COUNT, ANOTHER_EMOTICON_COUNT, ANOTHER_EMOTICON_COUNT, ANOTHER_EMOTICON_COUNT, ANOTHER_EMOTICON_COUNT);
    }

    @Test
    @DisplayName("EmoticonCountResponseDto 생성 테스트")
    void testEmoticonCountResponseDtoSave() {
        EmoticonCountResponseDto emoticonCountResponseDto = testEmoticonCountResponseDto();

        assertEquals(EMOTICON1_COUNT, emoticonCountResponseDto.getEmoticon1());
        assertEquals(ANOTHER_EMOTICON_COUNT, emoticonCountResponseDto.getEmoticon2());
        assertEquals(ANOTHER_EMOTICON_COUNT, emoticonCountResponseDto.getEmoticon3());
        assertEquals(ANOTHER_EMOTICON_COUNT, emoticonCountResponseDto.getEmoticon4());
        assertEquals(ANOTHER_EMOTICON_COUNT, emoticonCountResponseDto.getEmoticon5());
    }
}