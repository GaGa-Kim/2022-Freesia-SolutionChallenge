package com.freesia.imyourfreesia.service.youtube;

import com.freesia.imyourfreesia.domain.youtube.Youtube;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface YoutubeService {
    /**
     * 키워드로 유튜브 영상을 저장한다.
     */
    void getYoutubeData();

    /**
     * 유튜브 영상 목록을 조회한다.
     *
     * @return List<Youtube> (유튜브 영상 목록)
     */
    @Transactional(readOnly = true)
    List<Youtube> findAll();
}
