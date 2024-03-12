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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"MyPage API"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final ChallengeService challengeService;
    private final CommunityService communityService;
    private final LikeService likeService;

    @GetMapping("/user")
    @ApiOperation(value = "유저 정보 조회", notes = "유저 정보 조회 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<UserResponseDto> loadUser(@RequestParam String email) {
        return ResponseEntity.ok().body(userService.findUserDetailsByEmail(email));
    }

    @GetMapping(value = "/user/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "유저 프로필 이미지 ByteArray 조회", notes = "유저 프로필 이미지 ByteArray 조회 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<String> getUserImage(@RequestParam String email) throws IOException {
        return ResponseEntity.ok().body(userService.getUserProfileByteArray(email));
    }

    @PutMapping("/user")
    @ApiOperation(value = "유저 정보 수정", notes = "유저 정보 수정 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam String email,
                                                      @Valid UserRequestVO userRequestVO) throws Exception {
        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder().userRequestVO(userRequestVO).build();
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = GoalMsgUpdateRequestDto.builder().userRequestVO(userRequestVO).build();
        return ResponseEntity.ok().body(userService.update(email, requestDto, goalMsgUpdateRequestDto, userRequestVO.getProfileImg()));
    }

    @PutMapping("/user/pw")
    @ApiOperation(value = "유저 비밀번호 수정", notes = "유저 비밀번호 수정 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<UserResponseDto> updateUserPw(@RequestParam @Email String email,
                                                        @RequestBody @Valid UserPasswordUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(userService.updatePw(email, requestDto));
    }

    @GetMapping("/mypage/challenge")
    @ApiOperation(value = "마이페이지 챌린지 조회", notes = "마이페이지 챌린지 조회 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<List<ChallengeListResponseDto>> loadMyChallenge(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(challengeService.findChallengeByUser(email));
    }

    @GetMapping("/mypage/community")
    @ApiOperation(value = "마이페이지 커뮤니티 조회", notes = "마이페이지 커뮤니티 조회 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<List<CommunityListResponseDto>> loadMyCommunity(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(communityService.findCommunityByUser(email));
    }

    @GetMapping("/mypage/bookmark")
    @ApiOperation(value = "마이페이지 북마크 조회", notes = "마이페이지 북마크 조회 API")
    @ApiImplicitParam(name = "email", value = "유저 email")
    public ResponseEntity<List<LikeListResponseDto>> loadMyBookmark(@RequestParam @Email String email) {
        return ResponseEntity.ok().body(likeService.findLikeByUser(email));
    }
}
