package com.freesia.imyourfreesia.dto.user;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GoalMsgUpdateRequestDtoTest {
    private final ValidatorUtil<GoalMsgUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private GoalMsg goalMsg;
    private UserRequestVO userRequestVO;

    public static GoalMsgUpdateRequestDto testGoalMsgUpdateRequestDto(UserRequestVO userRequestVO) {
        return GoalMsgUpdateRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(UserTest.testUser());
        userRequestVO = UserRequestVOTest.testUserRequestVO(goalMsg.getUser(), goalMsg);
    }

    @Test
    @DisplayName("GoalMsgUpdateRequestDto 생성 테스트")
    void testGoalMsgUpdateRequestDtoSave() {
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = testGoalMsgUpdateRequestDto(userRequestVO);

        assertEquals(goalMsg.getGoalMsg(), goalMsgUpdateRequestDto.getGoalMsg());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = new GoalMsgUpdateRequestDto();

        assertNotNull(goalMsgUpdateRequestDto);
        assertNull(goalMsgUpdateRequestDto.getGoalMsg());
    }

    @Test
    @DisplayName("목표 메시지 유효성 검증 테스트")
    void username_validation() {
        userRequestVO.setGoalMsg(INVALID_EMPTY);
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = GoalMsgUpdateRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(goalMsgUpdateRequestDto);
    }
}