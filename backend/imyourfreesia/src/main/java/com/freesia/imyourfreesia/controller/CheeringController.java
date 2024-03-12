package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.service.cheering.CheeringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

@Api(tags = {"Cheering API (응원 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CheeringController {
    private final CheeringService cheeringService;

    @PostMapping("/api/cheering")
    @ApiOperation(value = "응원 설정", notes = "응원 설정 API")
    public ResponseEntity<Cheering> Cheering(@RequestBody @Valid CheeringSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(cheeringService.saveCheering(requestDto));
    }

    @DeleteMapping("/api/cheering")
    @ApiOperation(value = "응원 해제", notes = "응원 해제 API")
    @ApiImplicitParam(name = "id", value = "응원 id", example = "1")
    public ResponseEntity<?> UnCheering(@RequestParam @NotNull Long id) {
        cheeringService.deleteCheering(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cheering/cnt")
    @ApiOperation(value = "응원 전체 개수 조회", notes = "응원 전체 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 email")
    public ResponseEntity<Long> countCheering(@RequestParam @NotBlank String userEmail) {
        return ResponseEntity.ok().body(cheeringService.countByRecipientEmail(userEmail));
    }

    @GetMapping("/cheering/cnt/week")
    @ApiOperation(value = "응원 일주일 개수 조회", notes = "응원 일주일 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 email")
    public ResponseEntity<Long> countCheeringWeek(@RequestParam @NotBlank String userEmail) {
        return ResponseEntity.ok().body(cheeringService.countByCreatedDateBetweenAndRecipientEmail(userEmail));
    }

    @GetMapping("/cheering/ranking")
    @ApiOperation(value = "응원 랭킹 Top 10 조회", notes = "응원 랭킹 Top 10 API")
    public ResponseEntity<List<Map<String, Object>>> ranking() {
        return ResponseEntity.ok().body(cheeringService.cheeringRanking());
    }

    @GetMapping("/api/cheering/mycheer/list")
    @ApiOperation(value = "응원한 회원 목록 조회", notes = "응원한 회원 목록 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 email")
    public ResponseEntity<List<Cheering>> getMyCheerList(@RequestParam @NotBlank String userEmail) {
        return ResponseEntity.ok().body(cheeringService.findCheeringByUser(userEmail));
    }

    @GetMapping("/api/cheering/mycheer")
    @ApiOperation(value = "상대방 응원 여부 조회", notes = "상대방 응원 여부 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myEmail", value = "내 email"),
            @ApiImplicitParam(name = "yourEmail", value = "상대방 email")
    })
    public ResponseEntity<Boolean> getMyCheer(@RequestParam @NotBlank String myEmail,
                                              @RequestParam @NotBlank String yourEmail) {
        return ResponseEntity.ok().body(cheeringService.exitsBySenderEmailAndRecipientEmail(myEmail, yourEmail));
    }
}
