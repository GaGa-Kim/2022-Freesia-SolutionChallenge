package com.freesia.imyourfreesia.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import java.time.LocalDate;
import java.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserResponseDtoTest {
    private User user;
    private GoalMsg goalMsg;

    public static UserResponseDto testUserResponseDto(User user, GoalMsg goalMsg) {
        return new UserResponseDto(user, goalMsg);
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
    }

    @Test
    @DisplayName("UserResponseDto 생성 테스트")
    void testUserResponseDtoSave() {
        UserResponseDto userResponseDto = testUserResponseDto(user, goalMsg);

        assertEquals(user.getId(), userResponseDto.getUserId());
        assertEquals(user.getUsername(), userResponseDto.getUsername());
        assertEquals(user.getLoginId(), userResponseDto.getLoginId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickName(), userResponseDto.getNickName());
        assertEquals(Period.between(goalMsg.getModifiedDate(), LocalDate.now()).getDays() + 1, userResponseDto.getDays());
        assertEquals(goalMsg.getGoalMsg(), userResponseDto.getGoalMsg());
        assertEquals(goalMsg.getModifiedDate(), userResponseDto.getGoalMsgModifiedDate());
    }
}