package com.freesia.imyourfreesia.domain.youtube;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class YoutubeTest {
    public static final Long YOUTUBE_ID = 1L;
    public static final String YOUTUBE_TITLE = "제목";
    public static final String YOUTUBE_VIDEO_ID = "tbR4VEYEqSA";
    public static final String YOUTUBE_THUMBNAIL = "thumbnail.jpg";
    private Youtube youtube;

    public static Youtube testYoutube() {
        return Youtube.builder()
                .id(YOUTUBE_ID)
                .title(YOUTUBE_TITLE)
                .videoId(YOUTUBE_VIDEO_ID)
                .thumbnail(YOUTUBE_THUMBNAIL)
                .build();
    }

    @BeforeEach
    void setUp() {
        youtube = testYoutube();
    }

    @Test
    @DisplayName("유튜브 추가 테스트")
    void testYoutubeSave() {
        assertNotNull(youtube);
        assertEquals(YOUTUBE_ID, youtube.getId());
        assertEquals(YOUTUBE_TITLE, youtube.getTitle());
        assertEquals(YOUTUBE_VIDEO_ID, youtube.getVideoId());
        assertEquals(YOUTUBE_THUMBNAIL, youtube.getThumbnail());
    }
}