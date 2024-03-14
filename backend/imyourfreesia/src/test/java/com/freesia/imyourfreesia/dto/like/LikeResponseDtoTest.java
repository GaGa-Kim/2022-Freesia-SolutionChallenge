package com.freesia.imyourfreesia.dto.like;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LikeResponseDtoTest {
    private Like like;

    public static LikeResponseDto testLikeResponseDto(Like like) {
        return new LikeResponseDto(like);
    }

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(UserTest.testUser());
        like.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("LikeResponseDto 생성 테스트")
    void testLikeResponseDtoSave() {
        LikeResponseDto likeResponseDto = testLikeResponseDto(like);

        assertEquals(like.getId(), likeResponseDto.getLikeId());
        assertEquals(like.getUser().getId(), likeResponseDto.getUserId());
        assertEquals(like.getCommunity().getId(), likeResponseDto.getCommunityId());
    }
}