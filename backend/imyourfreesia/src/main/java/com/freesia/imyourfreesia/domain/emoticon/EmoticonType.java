package com.freesia.imyourfreesia.domain.emoticon;

import com.freesia.imyourfreesia.except.UnexpectedValueException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmoticonType {
    EMOTICON1("emoticon1"),
    EMOTICON2("emoticon2"),
    EMOTICON3("emoticon3"),
    EMOTICON4("emoticon4"),
    EMOTICON5("emoticon5");

    private static final Map<String, EmoticonType> EMOTICON_MAP = new HashMap<>();

    static {
        for (EmoticonType type : EmoticonType.values()) {
            EMOTICON_MAP.put(type.getEmoticonName(), type);
        }
    }

    private final String emoticonName;

    public static EmoticonType findByEmoticonName(String name) {
        EmoticonType foundEmoticon = EMOTICON_MAP.get(name);
        if (foundEmoticon == null) {
            throw new UnexpectedValueException();
        }
        return foundEmoticon;
    }
}