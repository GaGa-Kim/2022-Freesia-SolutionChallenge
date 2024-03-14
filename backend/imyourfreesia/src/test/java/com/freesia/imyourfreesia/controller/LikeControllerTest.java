package com.freesia.imyourfreesia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDtoTest;
import com.freesia.imyourfreesia.dto.like.LikeResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeResponseDtoTest;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.like.LikeService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LikeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class LikeControllerTest {
    private Like like;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(UserTest.testUser());
        like.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("좋아요 저장 테스트")
    void testSave() throws Exception {
        LikeResponseDto likeResponseDto = LikeResponseDtoTest.testLikeResponseDto(like);
        when(likeService.saveLike(any())).thenReturn(likeResponseDto);

        LikeSaveRequestDto likeSaveRequestDto = LikeSaveRequestDtoTest.testLikeSaveRequestDto(like);
        mockMvc.perform(post("/api/likes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(likeSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(likeService).saveLike(any());
    }

    @Test
    @DisplayName("좋아요 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(likeService).deleteLike(anyLong());

        mockMvc.perform(delete("/api/likes")
                        .param("likeId", String.valueOf(like.getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(likeService).deleteLike(anyLong());
    }

    @Test
    @DisplayName("좋아요 목록 조회 테스트")
    void testList() throws Exception {
        List<LikeListResponseDto> likeListResponseDtoList = Collections.singletonList(LikeListResponseDtoTest.testLikeListResponseDto(like));
        when(likeService.getListListByCommunity(anyLong())).thenReturn(likeListResponseDtoList);

        mockMvc.perform(get("/likes")
                        .param("communityId", String.valueOf(like.getCommunity().getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(likeService).getListListByCommunity(anyLong());
    }

    @Test
    @DisplayName("커뮤니티에 따른 좋아요 개수 조회 테스트")
    void testCount() throws Exception {
        when(likeService.countByCommunity(anyLong())).thenReturn(1);

        mockMvc.perform(get("/likes/count")
                        .param("communityId", String.valueOf(like.getCommunity().getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(likeService).countByCommunity(anyLong());
    }
}