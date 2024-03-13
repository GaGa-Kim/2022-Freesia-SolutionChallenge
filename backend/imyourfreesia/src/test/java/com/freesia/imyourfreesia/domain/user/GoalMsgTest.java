package com.freesia.imyourfreesia.domain.user;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoalMsgTest {
    public static final Long GOAL_MSG_ID = 1L;
    public static final String GOAL_MSG = "꾸준히 노력하자.";
    public static final String NEW_GOAL_MSG = "꾸준히 노력합시다.";
    private GoalMsg goalMsg;

    public static GoalMsg testGoalMsg() {
        return GoalMsg.builder()
                .id(GOAL_MSG_ID)
                .goalMsg(GOAL_MSG)
                .build();
    }

    @BeforeEach
    void setUp() {
        goalMsg = testGoalMsg();
    }

    @Test
    @DisplayName("목표 추가 테스트")
    void testGoalMsgSave() {
        assertNotNull(goalMsg);
        assertEquals(GOAL_MSG_ID, goalMsg.getId());
        assertEquals(GOAL_MSG, goalMsg.getGoalMsg());
    }

    @Test
    @DisplayName("목표의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        goalMsg.setUser(user);

        assertEquals(user, goalMsg.getUser());
    }

    @Test
    @DisplayName("목표 수정 테스트")
    void testUpdate() {
        goalMsg.update(NEW_GOAL_MSG);

        assertEquals(NEW_GOAL_MSG, goalMsg.getGoalMsg());
    }
}