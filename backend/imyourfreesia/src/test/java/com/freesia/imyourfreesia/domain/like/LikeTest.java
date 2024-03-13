package com.freesia.imyourfreesia.domain.like;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LikeTest {
    public static final Long LIKE_ID = 1L;
    private Like like;

    public static Like testLike() {
        return Like.builder()
                .id(LIKE_ID)
                .build();
    }

    @BeforeEach
    void setUp() {
        like = testLike();
    }

    @Test
    @DisplayName("좋아요 추가 테스트")
    void testLikeSave() {
        assertNotNull(like);
        assertEquals(LIKE_ID, like.getId());
    }

    @Test
    @DisplayName("좋아요의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        like.setUser(user);

        assertEquals(user, like.getUser());
    }

    @Test
    @DisplayName("좋아요의 커뮤니티 연관관계 설정 테스트")
    void testSetCommunity() {
        Community community = mock(Community.class);
        like.setCommunity(community);

        assertEquals(community, like.getCommunity());
    }
}