package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.dto.auth.OAuth2LoginRequestDto;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
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
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Auth API (회원 가입 ・ 로그인 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value = "애플리케이션 상태 검사", notes = "애플리케이션 상태 검사 API")
    @GetMapping("/healthCheck")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "구글 로그인", notes = "구글 로그인 API")
    @PostMapping("/googleLogin")
    public ResponseEntity<TokenResponseDto> googleLogin(@RequestBody @Valid OAuth2LoginRequestDto requestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.loginWithSocial(requestDto.getAccessToken(), SocialProvider.GOOGLE, response));
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 API")
    @PostMapping("/kakaoLogin")
    public ResponseEntity<TokenResponseDto> kakaoLogin(@RequestBody @Valid OAuth2LoginRequestDto requestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.loginWithSocial(requestDto.getAccessToken(), SocialProvider.KAKAO, response));
    }

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인 API")
    @PostMapping("/naverLogin")
    public ResponseEntity<TokenResponseDto> naverLogin(@RequestBody @Valid OAuth2LoginRequestDto requestDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.loginWithSocial(requestDto.getAccessToken(), SocialProvider.NAVER, response));
    }

    @PostMapping("/emailAuth")
    @ApiOperation(value = "회원 가입 시 이메일 인증 코드 전송", notes = "이메일을 통해 인증 코드 전송 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<String> emailAuth(@RequestParam @Email String email) throws Exception {
        return ResponseEntity.ok(authService.sendAuthEmail(email));
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    @ApiOperation(value = "일반 회원 가입 ", notes = "일반 회원 가입 API")
    public ResponseEntity<UserResponseDto> register(UserRequestVO userRequestVO) throws Exception {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder().userRequestVO(userRequestVO).build();
        return ResponseEntity.ok(authService.register(userSaveRequestDto, userRequestVO.getProfileImg()));
    }

    @ApiOperation(value = "일반 로그인", notes = "일반 로그인 API")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginId", value = "회원 로그인 아이디", dataType = "String", example = "freesia123"),
            @ApiImplicitParam(name = "password", value = "회원 비밀번호", dataType = "String", example = "password")
    })
    public ResponseEntity<TokenResponseDto> login(@RequestParam @NotEmpty String loginId,
                                                  @RequestParam @NotEmpty String password, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(loginId, password, response));
    }
}