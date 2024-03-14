package com.freesia.imyourfreesia.domain.like;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.domain.user.UserTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LikeRepositoryTest {
    private Like like;

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(userRepository.save(UserTest.testUser()));
        like.setCommunity(communityRepository.save(CommunityTest.testCommunity()));
        like = likeRepository.save(like);
    }

    @AfterEach
    void clean() {
        likeRepository.deleteAll();
        communityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원에 따른 좋아요 목록 조회 테스트")
    void testFindByUser() {
        List<Like> foundLikeList = likeRepository.findByUser(like.getUser());

        assertNotNull(foundLikeList);
        assertEquals(1, foundLikeList.size());
        assertEquals(like.getUser().getId(), foundLikeList.get(0).getUser().getId());
    }
}