package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.service.cheering.CheeringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Cheering API"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CheeringController {
    private final CheeringService cheeringService;

    /* 응원 설정 */
    @ApiOperation(value = "응원 설정", notes = "응원 설정 API")
    @ApiImplicitParam(name = "CheeringSaveRequestDto", value = "응원 설정 Dto")
    @PostMapping("/api/cheering")
    public ResponseEntity<Cheering> Cheering(@RequestBody CheeringSaveRequestDto requestDto) {
        return ResponseEntity.ok()
                .body(cheeringService.cheering(requestDto));
    }

    /* 응원 해제 */
    @ApiOperation(value = "응원 해제", notes = "응원 해제 API")
    @ApiImplicitParam(name = "id", value = "응원 id", example = "1")
    @DeleteMapping("/api/cheering")
    public ResponseEntity<?> UnCheering(@RequestParam Long id) {
        cheeringService.unCheering(id);
        return ResponseEntity.noContent().build();
    }

    /* 응원 전체 개수 조회 */
    @ApiOperation(value = "응원 전체 개수 조회", notes = "응원 전체 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 email")
    @GetMapping("/cheering/cnt")
    public Long countCheering(@RequestParam String userEmail) {
        return cheeringService.countByYourEmail(userEmail);
    }

    /* 응원 일주일 개수 조회 */
    @ApiOperation(value = "응원 일주일 개수 조회", notes = "응원 일주일 개수 조회 API")
    @ApiImplicitParam(name = "userEmail", value = "조회할 유저 email")
    @GetMapping("/cheering/cnt/week")
    public Long countCheeringWeek(@RequestParam String userEmail) {
        return cheeringService.countByCreatedDateBetweenAndYourEmail(userEmail);
    }

    /* 응원 랭킹 Top 10 조회 */
    @ApiOperation(value = "응원 랭킹 Top 10 조회", notes = "응원 랭킹 Top 10 API")
    @GetMapping("/cheering/ranking")
    /*public List<Map.Entry<String, Long>> ranking() {
        return cheeringService.ranking();
    }*/
    public List<Map<String, Object>> ranking() {
        return cheeringService.ranking();
    }

    /* 내가 응원한 유저 아이디 조회 */
    @GetMapping("/api/cheering/mycheer/list")
    public ResponseEntity<List<Cheering>> getMyCheerList(@RequestParam String userEmail) {
        return ResponseEntity.ok()
                .body(cheeringService.findByMyEmail(userEmail));
    }

    /* 상대방 응원 여부 */
    @GetMapping("/api/cheering/mycheer")
    public ResponseEntity<Boolean> getMyCheer(@RequestParam String myEmail, String yourEmail) {
        return ResponseEntity.ok()
                .body(cheeringService.findByMyEmailAndYourEmail(myEmail, yourEmail));
    }
}
