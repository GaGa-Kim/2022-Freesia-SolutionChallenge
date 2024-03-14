package com.freesia.imyourfreesia.dto.user;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserUpdateRequestDtoTest {
    private final ValidatorUtil<UserUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private User user;
    private UserRequestVO userRequestVO;

    public static UserUpdateRequestDto testUserUpdateRequestDto(UserRequestVO userRequestVO) {
        return UserUpdateRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        GoalMsg goalMsg = GoalMsgTest.testGoalMsg();
        userRequestVO = UserRequestVOTest.testUserRequestVO(user, goalMsg);
    }

    @Test
    @DisplayName("UserUpdateRequestDto 생성 테스트")
    void testUserUpdateRequestDtoSave() {
        UserUpdateRequestDto userUpdateRequestDto = testUserUpdateRequestDto(userRequestVO);

        assertEquals(user.getNickName(), userUpdateRequestDto.getNickName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto();

        assertNotNull(userUpdateRequestDto);
        assertNull(userUpdateRequestDto.getNickName());
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickName_validation() {
        userRequestVO.setNickName(INVALID_EMPTY);
        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userUpdateRequestDto);
    }
}