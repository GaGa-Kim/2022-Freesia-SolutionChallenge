package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.dto.auth.GeneralAuthVO;
import com.freesia.imyourfreesia.dto.auth.OAuth2LoginRequestDto;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Auth API (회원 가입 및 로그인 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "애플리케이션 상태 검사", notes = "애플리케이션 상태 검사 API")
    @GetMapping("/health_check")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "구글 로그인", notes = "구글 로그인 API")
    @PostMapping("/google")
    public ResponseEntity<TokenResponseDto> googleLogin(@RequestBody @Valid OAuth2LoginRequestDto googleLoginReqDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.socialLogin(googleLoginReqDto.getAccessToken(), SocialProvider.GOOGLE, response));
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 API")
    @PostMapping("/kakao")
    public ResponseEntity<TokenResponseDto> kakaoLogin(@RequestBody @Valid OAuth2LoginRequestDto kakaoLoginReqDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.socialLogin(kakaoLoginReqDto.getAccessToken(), SocialProvider.KAKAO, response));
    }

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인 API")
    @PostMapping("/naver")
    public ResponseEntity<TokenResponseDto> naverLogin(@RequestBody @Valid OAuth2LoginRequestDto naverLoginReqDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.socialLogin(naverLoginReqDto.getAccessToken(), SocialProvider.NAVER, response));
    }

    @PostMapping("/sendAuthEmail")
    @ApiOperation(value = "회원 가입 시 이메일 인증 코드 전송", notes = "이메일을 통해 인증 코드 전송 API")
    @ApiImplicitParam(name = "email", value = "이메일")
    public ResponseEntity<String> sendAuthEmail(@RequestParam @Email String email) throws Exception {
        return ResponseEntity.ok(authService.sendAuthEmail(email));
    }

    @ApiOperation(value = "일반 회원 가입 ", notes = "일반 회원 가입 API")
    @PostMapping(value = "/generalJoin", consumes = {"multipart/form-data"})
    public ResponseEntity<UserResponseDto> generalJoin(@Valid GeneralAuthVO generalAuthVO) throws Exception {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder().generalAuthVO(generalAuthVO).build();
        return ResponseEntity.ok(authService.generalJoin(userSaveRequestDto, generalAuthVO.getProfileImg()));
    }

    @ApiOperation(value = "일반 로그인", notes = "일반 로그인 API")
    @PostMapping("/generalLogin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", value = "사용자 아이디"),
            @ApiImplicitParam(name = "password", value = "사용자 비밀번호")
    })
    public ResponseEntity<TokenResponseDto> generalLogin(@RequestParam @NotBlank String loginId,
                                                         @RequestParam @NotBlank String password, HttpServletResponse response) {
        return ResponseEntity.ok(authService.generalLogin(loginId, password, response));
    }
}