package com.freesia.imyourfreesia.service.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.comment.CommentRepository;
import com.freesia.imyourfreesia.domain.comment.CommentTest;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.comment.CommentListResponseDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDtoTest;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDtoTest;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    private Comment comment;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private CommunityService communityService;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    void testSaveComment() {
        when(userService.findUserByEmail(anyString())).thenReturn(comment.getUser());
        when(communityService.findCommunityById(anyLong())).thenReturn(comment.getCommunity());
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDtoTest.testCommentSaveRequestDto(comment);
        List<CommentListResponseDto> result = commentService.saveComment(commentSaveRequestDto);

        assertNotNull(result);
        assertEquals(comment.getId(), result.get(0).getCommentId());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(comment.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(comment.getUser().getProfileImg(), result.get(0).getProfileImg());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getCommunity().getContent(), result.get(0).getContent());
        assertEquals(comment.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(comment.getModifiedDate(), result.get(0).getModifiedDate());

        verify(userService).findUserByEmail(anyString());
        verify(communityService).findCommunityById(anyLong());
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    @DisplayName("커뮤니티에 따른 댓글 목록 조회 테스트")
    void testGetCommentListByCommunity() {
        when(communityService.findCommunityById(anyLong())).thenReturn(comment.getCommunity());

        List<CommentListResponseDto> result = commentService.getCommentListByCommunity(comment.getId());

        assertNotNull(result);
        assertEquals(comment.getId(), result.get(0).getCommentId());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(comment.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(comment.getUser().getProfileImg(), result.get(0).getProfileImg());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getCommunity().getContent(), result.get(0).getContent());
        assertEquals(comment.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(comment.getModifiedDate(), result.get(0).getModifiedDate());

        verify(communityService).findCommunityById(anyLong());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void testUpdateComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(comment));
        when(communityService.findCommunityById(anyLong())).thenReturn(comment.getCommunity());

        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDtoTest.testCommentUpdateRequestDto(comment);
        List<CommentListResponseDto> result = commentService.updateComment(comment.getId(), commentUpdateRequestDto);

        assertNotNull(result);
        assertEquals(comment.getId(), result.get(0).getCommentId());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(comment.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(comment.getUser().getProfileImg(), result.get(0).getProfileImg());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getCommunity().getContent(), result.get(0).getContent());
        assertEquals(comment.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(comment.getModifiedDate(), result.get(0).getModifiedDate());

        verify(commentRepository).findById(anyLong());
        verify(communityService).findCommunityById(anyLong());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(anyLong());

        commentService.deleteComment(comment.getId());

        verify(commentRepository).deleteById(anyLong());
    }
}