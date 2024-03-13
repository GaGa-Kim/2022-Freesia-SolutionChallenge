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

    @PostMapping("/api/emoticons")
    @ApiOperation(value = "이모티콘 저장", notes = "이모티콘 저장 API")
    public ResponseEntity<EmoticonCountResponseDto> save(@RequestBody @Valid EmoticonRequestDto requestDto) {
        return ResponseEntity.ok().body(emoticonService.saveEmotion(requestDto));
    }

    @DeleteMapping("/api/emoticons")
    @ApiOperation(value = "이모티콘 삭제", notes = "이모티콘 삭제 API")
    public ResponseEntity<EmoticonCountResponseDto> delete(@RequestBody @Valid EmoticonRequestDto requestDto) {
        return ResponseEntity.ok().body(emoticonService.deleteEmotion(requestDto));
    }

    @GetMapping("/api/emoticons/my")
    @ApiOperation(value = "챌린지와 사용자에 따른 이모티콘 조회", notes = "챌린지와 사용자에 따른 이모티콘 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "challengeId", value = "챌린지 아이디", dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    })
    public ResponseEntity<EmoticonCountResponseDto> view(@RequestParam @NotNull Long challengeId,
                                                         @RequestParam @Email String email) {
        return ResponseEntity.ok().body(emoticonService.getEmoticonByChallengeAndUser(challengeId, email));
    }

    @GetMapping("/emoticons/count")
    @ApiOperation(value = "챌린지에 따른 이모티콘 갯수 조회", notes = "챌린지에 따른 이모티콘 갯수 조회 API")
    @ApiImplicitParam(name = "challengeId", value = "챌린지 아이디", dataType = "Long", example = "1")
    public ResponseEntity<EmoticonCountResponseDto> count(@RequestParam @NotNull Long challengeId) {
        return ResponseEntity.ok().body(emoticonService.countByChallenge(challengeId));
    }
}
