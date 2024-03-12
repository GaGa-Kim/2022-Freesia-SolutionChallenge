package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVO;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import com.freesia.imyourfreesia.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Challenge API (챌린지 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final FileService challengeFileServiceImpl;

    @PostMapping("/api/challenge")
    @ApiOperation(value = "챌린지 등록", notes = "챌린지 등록 API")
    @ApiImplicitParam(name = "challengeRequestVO", value = "챌린지 저장 VO")
    public ResponseEntity<ChallengeResponseDto> saveChallenge(@Valid ChallengeRequestVO challengeRequestVO) throws Exception {
        ChallengeSaveRequestDto requestDto = ChallengeSaveRequestDto.builder().challengeRequestVO(challengeRequestVO).build();
        return ResponseEntity.ok().body(challengeService.saveChallenge(requestDto, challengeRequestVO.getFiles()));
    }

    @GetMapping("/challenge/list")
    @ApiOperation(value = "챌린지 리스트 조회", notes = "챌린지 리스트 조회 API")
    public ResponseEntity<List<ChallengeListResponseDto>> findAllChallengeDesc() {
        return ResponseEntity.ok().body(challengeService.findAllChallengeDesc());
    }

    @GetMapping("/challenge")
    @ApiOperation(value = "챌린지 상세 조회", notes = "챌린지 상세 조회 API")
    @ApiImplicitParam(name = "id", value = "챌린지 id", example = "1")
    public ResponseEntity<ChallengeResponseDto> findChallengeById(@RequestParam @NotNull Long id) {
        return ResponseEntity.ok().body(challengeService.findChallengeById(id));
    }

    @PutMapping("/api/challenge")
    @ApiOperation(value = "챌린지 수정", notes = "챌린지 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "챌린지 id", example = "1"),
            @ApiImplicitParam(name = "challengeRequestVO", value = "챌린지 수정 VO")
    })
    public ResponseEntity<ChallengeResponseDto> updateChallenge(@RequestParam @NotNull Long id,
                                                                @Valid ChallengeRequestVO challengeRequestVO) throws Exception {
        ChallengeUpdateRequestDto requestDto = ChallengeUpdateRequestDto.builder().challengeRequestVO(challengeRequestVO).build();
        return ResponseEntity.ok().body(challengeService.updateChallenge(id, requestDto, challengeRequestVO.getFiles()));
    }

    @DeleteMapping("/api/challenge")
    @ApiOperation(value = "챌린지 삭제", notes = "챌린지 삭제 API")
    @ApiImplicitParam(name = "id", value = "챌린지 id", example = "1")
    public ResponseEntity<?> deleteChallenge(@RequestParam @NotNull Long id) {
        challengeService.deleteChallenge(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/challenge/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "챌린지 이미지 ByteArray 조회", notes = "챌린지 이미지 ByteArray 조회 API")
    @ApiImplicitParam(name = "id", value = "챌린지 이미지 id", example = "1")
    public ResponseEntity<String> getFileByteArray(@RequestParam Long id) throws IOException {
        return ResponseEntity.ok().body(challengeFileServiceImpl.getFileByteArray(id));
    }
}
