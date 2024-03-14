package com.freesia.imyourfreesia.domain.user;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GoalMsgRepositoryTest {
    private GoalMsg goalMsg;

    @Autowired
    private GoalMsgRepository goalMsgRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(userRepository.save(UserTest.testUser()));
        goalMsg = goalMsgRepository.save(goalMsg);
    }

    @AfterEach
    void clean() {
        goalMsgRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원으로 목표 조회 테스트")
    void testFindByUser() {
        GoalMsg foundGoalMsg = goalMsgRepository.findByUser(goalMsg.getUser());

        assertNotNull(foundGoalMsg);
        assertEquals(goalMsg.getUser().getId(), foundGoalMsg.getUser().getId());
    }

    @Test
    @DisplayName("회원으로 목표 존재 유무 조회 테스트")
    void testExistsByUser() {
        boolean foundGoalMsg = goalMsgRepository.existsByUser(goalMsg.getUser());

        assertTrue(foundGoalMsg);
    }
}