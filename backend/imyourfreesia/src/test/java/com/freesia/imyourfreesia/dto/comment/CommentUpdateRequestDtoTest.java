package com.freesia.imyourfreesia.dto.comment;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.comment.CommentTest;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentUpdateRequestDtoTest {
    private final ValidatorUtil<CommentUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private Comment comment;

    public static CommentUpdateRequestDto testCommentUpdateRequestDto(Comment comment) {
        return CommentUpdateRequestDto.builder()
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .build();
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("CommentUpdateRequestDto 생성 테스트")
    void testCommentUpdateRequestDtoSave() {
        CommentUpdateRequestDto commentSaveRequestDto = testCommentUpdateRequestDto(comment);

        assertEquals(comment.getId(), commentSaveRequestDto.getCommunityId());
        assertEquals(comment.getContent(), commentSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto();

        assertNotNull(commentUpdateRequestDto);
        assertNull(commentUpdateRequestDto.getCommunityId());
        assertNull(commentUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("댓글의 커뮤니티 아이디 유효성 검증 테스트")
    void communityId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .communityId(null)
                .content(comment.getContent())
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .communityId(comment.getCommunity().getId())
                .content(INVALID_EMPTY)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }
}