package com.freesia.imyourfreesia.domain.emoticon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.freesia.imyourfreesia.except.UnexpectedValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmoticonTypeTest {
    @Test
    @DisplayName("올바른 이모티콘 타입 열거형 값 테스트")
    void testFindByValidEmoticonName() {
        String validEmoticon = EmoticonType.EMOTICON1.getEmoticonName();

        EmoticonType emoticonType = EmoticonType.findByEmoticonName(validEmoticon);

        assertNotNull(emoticonType);
        assertEquals(EmoticonType.EMOTICON1, emoticonType);
    }

    @Test
    @DisplayName("올바르지 않은 이모티콘 타입 열거형 값 테스트")
    void testFindByInvalidEmoticonName() {
        String invalidEmoticon = "emoticon10";

        assertThatThrownBy(() -> EmoticonType.findByEmoticonName(invalidEmoticon))
                .isInstanceOf(UnexpectedValueException.class);
    }
}