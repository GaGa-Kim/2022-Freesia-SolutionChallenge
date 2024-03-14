package com.freesia.imyourfreesia.dto.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.comment.CommentTest;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentListResponseDtoTest {
    private Comment comment;

    public static CommentListResponseDto testCommentListResponseDto(Comment comment) {
        return new CommentListResponseDto(comment);
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("CommentListResponseDto 생성 테스트")
    void testCommentListResponseDtoSave() {
        CommentListResponseDto commentListResponseDto = testCommentListResponseDto(comment);

        assertEquals(comment.getId(), commentListResponseDto.getCommentId());
        assertEquals(comment.getUser().getId(), commentListResponseDto.getUserId());
        assertEquals(comment.getUser().getEmail(), commentListResponseDto.getEmail());
        assertEquals(comment.getUser().getNickName(), commentListResponseDto.getNickName());
        assertEquals(comment.getUser().getProfileImg(), commentListResponseDto.getProfileImg());
        assertEquals(comment.getCommunity().getId(), commentListResponseDto.getCommunityId());
        assertEquals(comment.getCommunity().getContent(), commentListResponseDto.getContent());
        assertEquals(comment.getCreatedDate(), commentListResponseDto.getCreatedDate());
        assertEquals(comment.getModifiedDate(), commentListResponseDto.getModifiedDate());
    }
}