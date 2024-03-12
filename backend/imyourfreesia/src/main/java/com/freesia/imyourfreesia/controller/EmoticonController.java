package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import com.freesia.imyourfreesia.service.emoticon.EmoticonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.Email;
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

@Api(tags = {"Emoticon API (이모티콘 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EmoticonController {
    private final EmoticonService emoticonService;

    @PostMapping("/api/emoticon")
    @ApiOperation(value = "이모티콘 저장", notes = "이모티콘 저장 API")
    public ResponseEntity<EmoticonCountResponseDto> save(@RequestBody @Valid EmoticonRequestDto requestDto) {
        return ResponseEntity.ok().body(emoticonService.saveEmotion(requestDto));
    }

    @DeleteMapping("/api/emoticon")
    @ApiOperation(value = "이모티콘 삭제", notes = "이모티콘 삭제 API")
    public ResponseEntity<EmoticonCountResponseDto> delete(@RequestBody @Valid EmoticonRequestDto requestDto) {
        return ResponseEntity.ok().body(emoticonService.deleteEmotion(requestDto));
    }

    @GetMapping("/api/emoticon/my")
    @ApiOperation(value = "글 아이디와 사용자에 따른 이모티콘 조회", notes = "글 아이디와 사용자에 따른 이모티콘 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "challengeId", value = "챌린지 게시글 id", example = "1"),
            @ApiImplicitParam(name = "email", value = "사용자 이메일")
    })
    public ResponseEntity<EmoticonCountResponseDto> findByChallengeIdAndUidAndEmoticonName(@RequestParam @NotNull Long challengeId,
                                                                                           @RequestParam @Email String email) {
        return ResponseEntity.ok().body(emoticonService.findByChallengeIdAndUidAndEmoticonName(challengeId, email));
    }

    @GetMapping("/emoticon/count")
    @ApiOperation(value = "글에 따른 이모티콘 갯수 조회", notes = "글에 따른 이모티콘 갯수 조회 API")
    public ResponseEntity<EmoticonCountResponseDto> count(@RequestParam @NotNull Long challengeId) {
        return ResponseEntity.ok().body(emoticonService.count(challengeId));
    }
}
