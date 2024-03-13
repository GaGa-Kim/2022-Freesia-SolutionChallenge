package com.freesia.imyourfreesia.dto.comment;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
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

public class CommentSaveRequestDtoTest {
    private final ValidatorUtil<CommentSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private Comment comment;

    public static CommentSaveRequestDto testCommentSaveRequestDto(Comment comment) {
        return CommentSaveRequestDto.builder()
                .email(comment.getUser().getEmail())
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
    @DisplayName("CommentSaveRequestDto 생성 테스트")
    void testCommentSaveRequestDtoSave() {
        CommentSaveRequestDto commentSaveRequestDto = testCommentSaveRequestDto(comment);

        assertEquals(comment.getUser().getEmail(), commentSaveRequestDto.getEmail());
        assertEquals(comment.getId(), commentSaveRequestDto.getCommunityId());
        assertEquals(comment.getContent(), commentSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("CommentSaveRequestDto toEntity 생성 테스트")
    void testCommentSaveRequestDtoEntity() {
        CommentSaveRequestDto commentSaveRequestDto = testCommentSaveRequestDto(comment);

        Comment commentEntity = commentSaveRequestDto.toEntity();
        commentEntity.setUser(comment.getUser());
        commentEntity.setCommunity(comment.getCommunity());

        assertNotNull(commentEntity);
        assertEquals(commentSaveRequestDto.getEmail(), commentEntity.getUser().getEmail());
        assertEquals(commentSaveRequestDto.getCommunityId(), commentEntity.getCommunity().getId());
        assertEquals(commentSaveRequestDto.getContent(), commentEntity.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto();

        assertNotNull(commentSaveRequestDto);
        assertNull(commentSaveRequestDto.getEmail());
        assertNull(commentSaveRequestDto.getCommunityId());
        assertNull(commentSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("댓글 작성 회원 이메일 유효성 검증 테스트")
    void email_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("댓글의 커뮤니티 아이디 유효성 검증 테스트")
    void communityId_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(comment.getUser().getEmail())
                .communityId(null)
                .content(comment.getContent())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .email(comment.getUser().getEmail())
                .communityId(comment.getCommunity().getId())
                .content(INVALID_EMPTY)
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }
}