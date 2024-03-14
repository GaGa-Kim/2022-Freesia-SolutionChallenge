package com.freesia.imyourfreesia.service.youtube;

import com.freesia.imyourfreesia.domain.youtube.Youtube;
import com.freesia.imyourfreesia.domain.youtube.YoutubeRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class YoutubeServiceImpl implements YoutubeService {
    private final YoutubeRepository youtubeRepository;
    private final YoutubeCrawler youtubeCrawler;

    @Override
    @PostConstruct
    public void getYoutubeData() {
        List<Youtube> youtubeList = new ArrayList<>(youtubeCrawler.crawlYoutubeVideos());
        youtubeRepository.saveAll(youtubeList);
    }

    @Override
    public List<Youtube> findAll() {
        return youtubeRepository.findAll();
    }
}
