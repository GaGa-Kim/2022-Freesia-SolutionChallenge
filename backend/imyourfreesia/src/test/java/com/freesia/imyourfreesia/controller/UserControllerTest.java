package com.freesia.imyourfreesia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDtoTest;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDtoTest;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDtoTest;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDtoTest;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.like.LikeService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class UserControllerTest {
    private User user;
    private GoalMsg goalMsg;
    private Challenge challenge;
    private Community community;
    private Like like;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ChallengeService challengeService;
    @MockBean
    private CommunityService communityService;
    @MockBean
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(user);
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(user);
        community = CommunityTest.testCommunity();
        community.setUser(user);
        like = LikeTest.testLike();
        like.setUser(user);
        like.setCommunity(community);
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void testView() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user, goalMsg);
        when(userService.getUserByEmail(anyString())).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/user")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).getUserByEmail(anyString());
    }

    @Test
    @DisplayName("회원 프로필 이미지 ByteArray 조회 테스트")
    void testProfileByeArray() throws Exception {
        byte[] profileByteArray = user.getProfileImg().getBytes();
        when(userService.getUserProfileByteArray(anyString())).thenReturn(Base64.getEncoder().encodeToString(profileByteArray));

        mockMvc.perform(get("/api/user/profile")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).getUserProfileByteArray(anyString());
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void testUpdate() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user, goalMsg);
        when(userService.update(anyString(), any(), any(), any(MultipartFile.class))).thenReturn(userResponseDto);

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/user");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        UserRequestVO userRequestVO = UserRequestVOTest.testUserRequestVO(user, goalMsg);
        mockMvc.perform(builder
                        .file((MockMultipartFile) userRequestVO.getProfileImg())
                        .param("email", userRequestVO.getEmail())
                        .param("nickName", userRequestVO.getNickName())
                        .param("goalMsg", userRequestVO.getGoalMsg())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).update(anyString(), any(), any(), any(MultipartFile.class));
    }

    @Test
    @DisplayName("회원 비밀번호 수정 테스트")
    void testUpdatePassword() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user, goalMsg);
        when(userService.updatePw(anyString(), any())).thenReturn(userResponseDto);

        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = UserPasswordUpdateRequestDtoTest.testUserPasswordUpdateRequestDto(user);
        mockMvc.perform(put("/api/user/pw")
                        .param("email", user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userPasswordUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).updatePw(anyString(), any());
    }

    @Test
    @DisplayName("마이페이지 챌린지 조회 테스트")
    void testMyChallengeList() throws Exception {
        List<ChallengeListResponseDto> challengeListResponseDtoList = Collections.singletonList(ChallengeListResponseDtoTest.testChallengeListResponseDto(challenge));
        when(challengeService.getChallengeListByUser(anyString())).thenReturn(challengeListResponseDtoList);

        mockMvc.perform(get("/api/mypage/challenge")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).getChallengeListByUser(anyString());
    }

    @Test
    @DisplayName("마이페이지 커뮤니티 조회 테스트")
    void testMyCommunityList() throws Exception {
        List<CommunityListResponseDto> communityListResponseDtoList = Collections.singletonList(CommunityListResponseDtoTest.testCommunityListResponseDto(community));
        when(communityService.getCommunityListByUser(anyString())).thenReturn(communityListResponseDtoList);

        mockMvc.perform(get("/api/mypage/community")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getCommunityListByUser(anyString());
    }

    @Test
    @DisplayName("마이페이지 북마크 (좋아요) 조회 테스트")
    void testMyLikeList() throws Exception {
        List<LikeListResponseDto> likeListResponseDtoList = Collections.singletonList(LikeListResponseDtoTest.testLikeListResponseDto(like));
        when(likeService.getLikeListByUser(anyString())).thenReturn(likeListResponseDtoList);

        mockMvc.perform(get("/api/mypage/bookmark")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(likeService).getLikeListByUser(anyString());
    }
}