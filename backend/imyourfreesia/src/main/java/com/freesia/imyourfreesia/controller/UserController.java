package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDto;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.like.LikeService;
import com.freesia.imyourfreesia.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"MyPage API (회원 ・ 마이페이지 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final ChallengeService challengeService;
    private final CommunityService communityService;
    private final LikeService likeService;

    @GetMapping("/api/user")
    @ApiOperation(value = "회원 정보 조회", notes = "회원 정보 조회 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<UserResponseDto> view(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping(value = "/api/user/profile", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "회원 프로필 이미지 ByteArray 조회", notes = "회원 프로필 이미지 ByteArray 조회 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<String> profileByeArray(@RequestParam @Email String email) throws IOException {
        return ResponseEntity.ok().body(userService.getUserProfileByteArray(email));
    }

    @PutMapping("/api/user")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<UserResponseDto> update(@RequestParam @Email String email, UserRequestVO userRequestVO) throws Exception {
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder().userRequestVO(userRequestVO).build();
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = GoalMsgUpdateRequestDto.builder().userRequestVO(userRequestVO).build();
        return ResponseEntity.ok().body(userService.update(email, requestDto, goalMsgUpdateRequestDto, userRequestVO.getProfileImg()));
    }

    @PutMapping("/api/user/pw")
    @ApiOperation(value = "회원 비밀번호 수정", notes = "회원 비밀번호 수정 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<UserResponseDto> updatePassword(@RequestParam @Email String email,
                                                          @RequestBody @Valid UserPasswordUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(userService.updatePw(email, requestDto));
    }

    @GetMapping("/api/mypage/challenge")
    @ApiOperation(value = "마이페이지 챌린지 조회", notes = "마이페이지 챌린지 조회 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<List<ChallengeListResponseDto>> myChallengeList(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(challengeService.getChallengeListByUser(email));
    }

    @GetMapping("/api/mypage/community")
    @ApiOperation(value = "마이페이지 커뮤니티 조회", notes = "마이페이지 커뮤니티 조회 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<List<CommunityListResponseDto>> myCommunityList(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(communityService.getCommunityListByUser(email));
    }

    @GetMapping("/api/mypage/bookmark")
    @ApiOperation(value = "마이페이지 북마크 (좋아요) 조회", notes = "마이페이지 북마크 (좋아요) 조회 API")
    @ApiImplicitParam(name = "email", value = "회원 이메일", dataType = "String", example = "freesia@gmail.com")
    public ResponseEntity<List<LikeListResponseDto>> myLikeList(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(likeService.getLikeListByUser(email));
    }
}
