package com.freesia.imyourfreesia.domain.community;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
class CommunityRepositoryTest {
    private Community community;

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(userRepository.save(UserTest.testUser()));
        community = communityRepository.save(community);
    }

    @AfterEach
    void clean() {
        communityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리에 따른 커뮤니티 목록 조회 테스트")
    void testFindByCategory() {
        List<Community> foundCommunityList = communityRepository.findByCategory(community.getCategory());

        assertNotNull(foundCommunityList);
        assertEquals(1, foundCommunityList.size());
    }


    @Test
    @DisplayName("회원에 따른 커뮤니티 목록 조회 테스트")
    void testFindByUser() {
        List<Community> foundCommunityList = communityRepository.findByUser(community.getUser());

        assertNotNull(foundCommunityList);
        assertEquals(1, foundCommunityList.size());
        assertEquals(community.getUser().getId(), foundCommunityList.get(0).getUser().getId());
    }
}