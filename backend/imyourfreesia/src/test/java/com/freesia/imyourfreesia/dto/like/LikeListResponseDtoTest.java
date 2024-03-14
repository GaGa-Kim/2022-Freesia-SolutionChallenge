package com.freesia.imyourfreesia.dto.like;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LikeListResponseDtoTest {
    private Like like;

    public static LikeListResponseDto testLikeListResponseDto(Like like) {
        return new LikeListResponseDto(like);
    }

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(UserTest.testUser());
        like.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("LikeListResponseDto 생성 테스트")
    void testLikeListResponseDtoSave() {
        LikeListResponseDto likeListResponseDto = testLikeListResponseDto(like);

        assertEquals(like.getId(), likeListResponseDto.getLikeId());
        assertEquals(like.getUser().getId(), likeListResponseDto.getUserId());
        assertEquals(like.getCommunity().getId(), likeListResponseDto.getCommunityId());
        assertEquals(like.getCommunity().getTitle(), likeListResponseDto.getTitle());
        assertEquals(like.getCommunity().getContent(), likeListResponseDto.getContent());
        assertEquals(like.getCommunity().getCategory().getCategoryName(), likeListResponseDto.getCategory());
    }
}