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
import javax.validation.constraints.NotEmpty;
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

    @PostMapping("/api/cheerings")
    @ApiOperation(value = "응원 설정", notes = "응원 설정 API")
    public ResponseEntity<Cheering> save(@RequestBody @Valid CheeringSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(cheeringService.saveCheering(requestDto));
    }

    @DeleteMapping("/api/cheerings")
    @ApiOperation(value = "응원 해제", notes = "응원 해제 API")
    @ApiImplicitParam(name = "cheeringId", value = "응원 아이디", dataType = "Long", example = "1")
    public ResponseEntity<?> delete(@RequestParam @NotNull Long cheeringId) {
        cheeringService.deleteCheering(cheeringId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cheerings/count")
    @ApiOperation(value = "응원 전체 개수 조회", notes = "응원 전체 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<Long> countAll(@RequestParam @NotEmpty String userEmail) {
        return ResponseEntity.ok().body(cheeringService.countByRecipientEmail(userEmail));
    }

    @GetMapping("/cheerings/count/week")
    @ApiOperation(value = "응원 일주일 개수 조회", notes = "응원 일주일 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<Long> countWeek(@RequestParam @NotEmpty String userEmail) {
        return ResponseEntity.ok().body(cheeringService.countByCreatedDateBetweenAndRecipientEmail(userEmail));
    }

    @GetMapping("/cheerings/ranking")
    @ApiOperation(value = "응원 랭킹 Top 10 조회", notes = "응원 랭킹 Top 10 API")
    public ResponseEntity<List<Map<String, Object>>> rank() {
        return ResponseEntity.ok().body(cheeringService.cheeringRankList());
    }

    @GetMapping("/api/cheerings/myList")
    @ApiOperation(value = "응원한 회원 목록 조회", notes = "응원한 회원 목록 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<List<Cheering>> myList(@RequestParam @NotEmpty String userEmail) {
        return ResponseEntity.ok().body(cheeringService.findCheeringListByUser(userEmail));
    }

    @GetMapping("/api/cheerings/exists")
    @ApiOperation(value = "상대방 응원 여부 조회", notes = "상대방 응원 여부 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "myEmail", value = "본인 이메일", dataType = "String", example = "freesia@gmail.com"),
            @ApiImplicitParam(name = "yourEmail", value = "상대방 이메일", dataType = "String", example = "reboot@gmail.com")
    })
    public ResponseEntity<Boolean> existsCheering(@RequestParam @NotEmpty String myEmail,
                                                  @RequestParam @NotEmpty String yourEmail) {
        return ResponseEntity.ok().body(cheeringService.exitsBySenderEmailAndRecipientEmail(myEmail, yourEmail));
    }
}
