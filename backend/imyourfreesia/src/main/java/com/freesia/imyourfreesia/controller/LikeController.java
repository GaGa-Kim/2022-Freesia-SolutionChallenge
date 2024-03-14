package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDto;
import com.freesia.imyourfreesia.service.like.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Like API (좋아요 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/api/likes")
    @ApiOperation(value = "좋아요 설정", notes = "좋아요 설정 API")
    public ResponseEntity<LikeResponseDto> save(@RequestBody @Valid LikeSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(likeService.saveLike(requestDto));
    }

    @DeleteMapping("/api/likes")
    @ApiOperation(value = "좋아요 해제", notes = "좋아요 해제 API")
    @ApiImplicitParam(name = "likeId", value = "좋아요 아이디", dataType = "Long", example = "1")
    public ResponseEntity<?> delete(@RequestParam @NotNull Long likeId) {
        likeService.deleteLike(likeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/likes")
    @ApiOperation(value = "좋아요 목록 조회", notes = "좋아요 목록 조회 API")
    @ApiImplicitParam(name = "communityId", value = "커뮤니티 아이디", dataType = "Long", example = "1")
    public ResponseEntity<List<LikeListResponseDto>> list(@RequestParam @NotNull Long communityId) {
        return ResponseEntity.ok().body(likeService.getListListByCommunity(communityId));
    }

    @GetMapping("/likes/count")
    @ApiOperation(value = "챌린지에 따른 좋아요 개수 조회", notes = "좋아요 개수 조회 API")
    @ApiImplicitParam(name = "communityId", value = "커뮤니티 아이디", dataType = "Long", example = "1")
    public ResponseEntity<Integer> count(@RequestParam @NotNull Long communityId) {
        return ResponseEntity.ok().body(likeService.countByCommunity(communityId));
    }
}
