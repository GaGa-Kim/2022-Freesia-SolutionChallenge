package com.freesia.imyourfreesia.domain.emoticon;

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

    private final String emoticonName;
}