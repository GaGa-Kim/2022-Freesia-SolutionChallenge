package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.youtube.Youtube;
import com.freesia.imyourfreesia.service.youtube.YoutubeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Youtube API"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class YoutubeController {
    private final YoutubeService youtubeService;

    /*유튜브 영상 조회*/
    @ApiOperation(value = "유튜브 영상 조회", notes = "유튜브 영상 조회 API")
    @GetMapping("/youtube")
    public ResponseEntity<List<Youtube>> loadYoutube() {
        return ResponseEntity.ok()
                .body(youtubeService.findAll());
    }
}

