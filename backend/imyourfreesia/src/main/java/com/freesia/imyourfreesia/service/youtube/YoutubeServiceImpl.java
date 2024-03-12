package com.freesia.imyourfreesia.service.youtube;

import com.freesia.imyourfreesia.config.GlobalConfig;
import com.freesia.imyourfreesia.domain.youtube.Youtube;
import com.freesia.imyourfreesia.domain.youtube.YoutubeRepository;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import java.io.IOException;
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
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String QUERY_KEYWORD = "경력단절여성";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private final YoutubeRepository youtubeRepository;
    private final GlobalConfig config;

    @Override
    @PostConstruct
    public void getYoutubeData() {
        try {
            YouTube youtube = getYoutubeClient();
            YouTube.Search.List search = youtube.search().list("snippet");

            search.setKey(config.getYoutubeKey());
            search.setQ(QUERY_KEYWORD);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<Youtube> youtubeList = getAndSaveYoutubeVideos(searchResponse.getItems());
            if (youtubeList.isEmpty()) {
                log.error(QUERY_KEYWORD + " 키워드의 영상이 없습니다.");
            }
        } catch (GoogleJsonResponseException e) {
            log.error(e.getDetails().getMessage());
        } catch (Throwable t) {
            log.error(t.getMessage());
        }
    }

    @Override
    public List<Youtube> findAll() {
        return youtubeRepository.findAll();
    }

    private YouTube getYoutubeClient() {
        HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }
        };
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, httpRequestInitializer)
                .setApplicationName("youtube-cmdline-search-sample")
                .build();
    }

    private List<Youtube> getAndSaveYoutubeVideos(List<SearchResult> searchResults) {
        List<Youtube> youtubeList = new ArrayList<>();
        for (SearchResult singleVideo : searchResults) {
            ResourceId rId = singleVideo.getId();
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                Youtube youtube = Youtube.builder()
                        .title(singleVideo.getSnippet().getTitle())
                        .videoId(rId.getVideoId())
                        .thumbnail(thumbnail.getUrl())
                        .build();
                youtubeList.add(youtube);
            }
        }
        return youtubeRepository.saveAll(youtubeList);
    }
}
