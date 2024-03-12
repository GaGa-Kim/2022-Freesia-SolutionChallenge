package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.center.Center;
import com.freesia.imyourfreesia.service.center.CenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Center API (센터 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CenterController {
    private final CenterService centerService;

    @GetMapping("/center")
    @ApiOperation(value = "지역 센터 정보 검색", notes = "지역 센터 정보 검색 API")
    @ApiImplicitParam(name = "address", value = "주소")
    public ResponseEntity<List<Center>> findCenterListByAddress(@RequestParam @NotNull String address) {
        return ResponseEntity.ok().body(centerService.findCenterListByAddress(address));

    }

    @GetMapping("/centers")
    @ApiOperation(value = "지역 센터 정보 전체 조회", notes = "지역 센터 전체 조회 API")
    public ResponseEntity<List<Center>> findAllCenterList() {
        return ResponseEntity.ok().body(centerService.findAllCenterList());

    }
}