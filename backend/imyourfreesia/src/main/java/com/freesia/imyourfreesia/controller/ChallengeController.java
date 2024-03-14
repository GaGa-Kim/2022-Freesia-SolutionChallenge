package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVO;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Challenge API (챌린지 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChallengeController {
    private final ChallengeService challengeService;

    @PostMapping("/api/challenges")
    @ApiOperation(value = "챌린지 저장", notes = "챌린지 저장 API")
    public ResponseEntity<ChallengeResponseDto> save(ChallengeRequestVO requestVO) throws Exception {
        ChallengeSaveRequestDto requestDto = ChallengeSaveRequestDto.builder().challengeRequestVO(requestVO).build();
        return ResponseEntity.ok().body(challengeService.saveChallenge(requestDto, requestVO.getFiles()));
    }

    @GetMapping("/challenges")
    @ApiOperation(value = "챌린지 리스트 조회", notes = "챌린지 리스트 조회 API")
    public ResponseEntity<List<ChallengeListResponseDto>> list() {
        return ResponseEntity.ok().body(challengeService.getAllChallengeList());
    }

    @GetMapping("/challenges/{challengeId}")
    @ApiOperation(value = "챌린지 상세 조회", notes = "챌린지 상세 조회 API")
    @ApiImplicitParam(name = "challengeId", value = "챌린지 아이디", dataType = "Long", example = "1")
    public ResponseEntity<ChallengeResponseDto> view(@PathVariable @NotNull Long challengeId) {
        return ResponseEntity.ok().body(challengeService.getChallengeById(challengeId));
    }

    @PutMapping("/api/challenges/{challengeId}")
    @ApiOperation(value = "챌린지 수정", notes = "챌린지 수정 API")
    @ApiImplicitParam(name = "challengeId", value = "챌린지 아이디", dataType = "Long", example = "1")
    public ResponseEntity<ChallengeResponseDto> update(@PathVariable @NotNull Long challengeId, ChallengeRequestVO requestVO) throws Exception {
        ChallengeUpdateRequestDto requestDto = ChallengeUpdateRequestDto.builder().challengeRequestVO(requestVO).build();
        return ResponseEntity.ok().body(challengeService.updateChallenge(challengeId, requestDto, requestVO.getFiles()));
    }

    @DeleteMapping("/api/challenges/{challengeId}")
    @ApiOperation(value = "챌린지 삭제", notes = "챌린지 삭제 API")
    @ApiImplicitParam(name = "challengeId", value = "챌린지 아이디", dataType = "Long", example = "1")
    public ResponseEntity<?> delete(@PathVariable @NotNull Long challengeId) {
        challengeService.deleteChallenge(challengeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/challenges/file/{fileId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "챌린지 파일 ByteArray 조회", notes = "챌린지 파일 ByteArray 조회 API")
    @ApiImplicitParam(name = "fileId", value = "챌린지 파일 아이디", dataType = "Long", example = "1")
    public ResponseEntity<String> fileByteArray(@PathVariable @NotNull Long fileId) throws IOException {
        return ResponseEntity.ok().body(challengeService.getFileByteArray(fileId));
    }
}