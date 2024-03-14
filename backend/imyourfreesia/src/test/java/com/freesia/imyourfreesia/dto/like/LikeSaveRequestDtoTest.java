package com.freesia.imyourfreesia.dto.like;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LikeSaveRequestDtoTest {
    private final ValidatorUtil<LikeSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private Like like;

    public static LikeSaveRequestDto testLikeSaveRequestDto(Like like) {
        return LikeSaveRequestDto.builder()
                .email(like.getUser().getEmail())
                .communityId(like.getCommunity().getId())
                .build();
    }

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(UserTest.testUser());
        like.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("LikeSaveRequestDto 생성 테스트")
    void testLikeSaveRequestDtoSave() {
        LikeSaveRequestDto likeSaveRequestDto = testLikeSaveRequestDto(like);

        assertEquals(like.getUser().getEmail(), likeSaveRequestDto.getEmail());
        assertEquals(like.getCommunity().getId(), likeSaveRequestDto.getCommunityId());
    }

    @Test
    @DisplayName("LikeSaveRequestDto toEntity 생성 테스트")
    void testLikeSaveRequestDtoEntity() {
        LikeSaveRequestDto likeSaveRequestDto = testLikeSaveRequestDto(like);

        Like likeEntity = likeSaveRequestDto.toEntity();
        likeEntity.setUser(like.getUser());
        likeEntity.setCommunity(like.getCommunity());

        assertNotNull(likeEntity);
        assertEquals(likeSaveRequestDto.getEmail(), likeEntity.getUser().getEmail());
        assertEquals(likeSaveRequestDto.getCommunityId(), likeEntity.getCommunity().getId());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        LikeSaveRequestDto likeSaveRequestDto = new LikeSaveRequestDto();

        assertNotNull(likeSaveRequestDto);
        assertNull(likeSaveRequestDto.getEmail());
        assertNull(likeSaveRequestDto.getCommunityId());
    }

    @Test
    @DisplayName("좋아요 작성 회원 이메일 유효성 검증 테스트")
    void email_validation() {
        LikeSaveRequestDto likeSaveRequestDto = LikeSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .communityId(like.getCommunity().getId())
                .build();

        validatorUtil.validate(likeSaveRequestDto);
    }

    @Test
    @DisplayName("좋아요의 커뮤니티 아이디 유효성 검증 테스트")
    void communityId_validation() {
        LikeSaveRequestDto likeSaveRequestDto = LikeSaveRequestDto.builder()
                .email(like.getUser().getEmail())
                .communityId(null)
                .build();

        validatorUtil.validate(likeSaveRequestDto);
    }
}