package com.freesia.imyourfreesia.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentTest {
    public static final Long COMMENT_ID = 1L;
    public static final String COMMENT_CONTENT = "내용";
    public static final String NEW_COMMENT_CONTENT = "새 내용";
    private Comment comment;

    public static Comment testComment() {
        return Comment.builder()
                .id(COMMENT_ID)
                .content(COMMENT_CONTENT)
                .build();
    }

    @BeforeEach
    void setUp() {
        comment = testComment();
    }

    @Test
    @DisplayName("댓글 추가 테스트")
    void testCommentSave() {
        assertNotNull(comment);
        assertEquals(COMMENT_ID, comment.getId());
        assertEquals(COMMENT_CONTENT, comment.getContent());
    }

    @Test
    @DisplayName("댓글의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        comment.setUser(user);

        assertEquals(user, comment.getUser());
    }

    @Test
    @DisplayName("댓글의 커뮤니티 연관관계 설정 테스트")
    void testSetCommunity() {
        Community community = mock(Community.class);
        comment.setCommunity(community);

        assertEquals(community, comment.getCommunity());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void testUpdate() {
        comment.update(NEW_COMMENT_CONTENT);

        assertEquals(NEW_COMMENT_CONTENT, comment.getContent());
    }
}