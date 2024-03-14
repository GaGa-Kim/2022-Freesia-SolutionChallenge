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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.comment.CommentTest;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.comment.CommentListResponseDto;
import com.freesia.imyourfreesia.dto.comment.CommentListResponseDtoTest;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDtoTest;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.comment.CommentService;
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

@WebMvcTest(controllers = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CommentControllerTest {
    private Comment comment;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    void testSave() throws Exception {
        List<CommentListResponseDto> commentListResponseDtoList = Collections.singletonList(CommentListResponseDtoTest.testCommentListResponseDto(comment));
        when(commentService.saveComment(any())).thenReturn(commentListResponseDtoList);

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDtoTest.testCommentSaveRequestDto(comment);
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(commentService).saveComment(any());
    }

    @Test
    @DisplayName("게시글 별 댓글 조회 테스트")
    void testList() throws Exception {
        List<CommentListResponseDto> commentListResponseDtoList = Collections.singletonList(CommentListResponseDtoTest.testCommentListResponseDto(comment));
        when(commentService.getCommentListByCommunity(anyLong())).thenReturn(commentListResponseDtoList);

        mockMvc.perform(get("/comments")
                        .param("communityId", String.valueOf(comment.getCommunity().getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(commentService).getCommentListByCommunity(anyLong());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void testUpdate() throws Exception {
        List<CommentListResponseDto> commentListResponseDtoList = Collections.singletonList(CommentListResponseDtoTest.testCommentListResponseDto(comment));
        when(commentService.updateComment(anyLong(), any())).thenReturn(commentListResponseDtoList);

        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDtoTest.testCommentUpdateRequestDto(comment);
        mockMvc.perform(put("/api/comments")
                        .param("commentId", String.valueOf(comment.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(commentService).updateComment(anyLong(), any());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(commentService).deleteComment(anyLong());

        mockMvc.perform(delete("/api/comments")
                        .param("commentId", String.valueOf(comment.getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(commentService).deleteComment(anyLong());
    }
}