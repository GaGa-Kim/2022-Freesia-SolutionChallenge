package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.likes.LikesListResponseDto;
import com.freesia.imyourfreesia.dto.likes.LikesSaveRequestDto;
import com.freesia.imyourfreesia.service.like.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Likes API"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LikeController {
    private final LikeService likeService;

    /* 좋아요 설정 */
    @ApiOperation(value = "좋아요 설정", notes = "좋아요 설정 API")
    @ApiImplicitParam(name = "LikeSaveRequestDto", value = "좋아요 설정 dto")
    @PostMapping("/api/like")
    public ResponseEntity<Long> likes(@RequestBody LikesSaveRequestDto requestDto) {
        return ResponseEntity.ok()
                .body(likeService.likes(requestDto));
    }

    /* 좋아요 해제 */
    @ApiOperation(value = "좋아요 해제", notes = "좋아요 해제 API")
    @ApiImplicitParam(name = "id", value = "좋아요 id", dataType = "Long", paramType = "query", example = "1")
    @DeleteMapping("/api/like")
    public ResponseEntity<?> unLikes(@RequestParam Long id) {
        likeService.unLikes(id);
        return ResponseEntity.noContent().build();
    }

    /* 좋아요 목록 조회 */
    @ApiOperation(value = "좋아요 목록 조회", notes = "좋아요 목록 조회 API")
    @ApiImplicitParam(name = "pid", value = "게시글 id", dataType = "Long", paramType = "query", example = "1")
    @GetMapping("/likes")
    public ResponseEntity<List<LikesListResponseDto>> loadLikes(@RequestParam Long pid) {
        return ResponseEntity.ok()
                .body(likeService.findAllByPid(pid));
    }

    /* 좋아요 개수 조회 */
    @ApiOperation(value = "좋아요 개수 조회", notes = "좋아요 개수 조회 API")
    @ApiImplicitParam(name = "pid", value = "게시글 id", dataType = "Long", paramType = "query", example = "1")
    @GetMapping("/like/cnt")
    public Long countLikes(@RequestParam Long pid) {
        return likeService.countByPid(pid);
    }
}
