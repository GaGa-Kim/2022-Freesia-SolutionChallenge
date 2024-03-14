package com.freesia.imyourfreesia.service.youtube;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.youtube.Youtube;
import com.freesia.imyourfreesia.domain.youtube.YoutubeRepository;
import com.freesia.imyourfreesia.domain.youtube.YoutubeTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class YoutubeServiceImplTest {
    private Youtube youtube;

    @Mock
    private YoutubeRepository youtubeRepository;
    @Mock
    private YoutubeCrawler youtubeCrawler;
    @InjectMocks
    private YoutubeServiceImpl youtubeService;

    @BeforeEach
    void setUp() {
        youtube = YoutubeTest.testYoutube();
    }

    @Test
    @DisplayName("유튜브 영상 정보 추출 후 저장 테스트")
    void testGetYoutubeData() {
        when(youtubeCrawler.crawlYoutubeVideos()).thenReturn(Collections.singletonList(youtube));
        when(youtubeRepository.saveAll(anyList())).thenReturn(Collections.singletonList(youtube));

        youtubeService.getYoutubeData();

        verify(youtubeCrawler).crawlYoutubeVideos();
        verify(youtubeRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("전체 유튜브 영상 목록 조회 테스트")
    void testFindAll() {
        when(youtubeRepository.findAll()).thenReturn(Collections.singletonList(youtube));

        List<Youtube> result = youtubeService.findAll();

        assertNotNull(result);
        assertEquals(youtube.getTitle(), result.get(0).getTitle());
        assertEquals(youtube.getVideoId(), result.get(0).getVideoId());
        assertEquals(youtube.getThumbnail(), result.get(0).getThumbnail());

        verify(youtubeRepository).findAll();
    }
}