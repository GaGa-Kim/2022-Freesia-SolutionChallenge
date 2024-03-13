package com.freesia.imyourfreesia.dto.auth;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSaveRequestDtoTest {
    private final ValidatorUtil<UserSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private User user;
    private GoalMsg goalMsg;
    private UserRequestVO userRequestVO;

    public static UserSaveRequestDto testUserSaveRequestDto(UserRequestVO userRequestVO) {
        return UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
        userRequestVO = UserRequestVOTest.testUserRequestVO(user, goalMsg);
    }

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDtoSave() {
        UserSaveRequestDto userSaveRequestDto = testUserSaveRequestDto(userRequestVO);

        assertEquals(user.getUsername(), userSaveRequestDto.getUsername());
        assertEquals(user.getLoginId(), userSaveRequestDto.getLoginId());
        assertEquals(user.getPassword(), userSaveRequestDto.getPassword());
        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        assertEquals(user.getNickName(), userSaveRequestDto.getNickName());
        assertEquals(goalMsg.getGoalMsg(), userSaveRequestDto.getGoalMsg());
    }

    @Test
    @DisplayName("UserSaveRequestDto toEntity 생성 테스트")
    void testUserSaveRequestDtoEntity() {
        UserSaveRequestDto userSaveRequestDto = testUserSaveRequestDto(userRequestVO);

        User userEntity = userSaveRequestDto.toEntity();
        userEntity.setPassword(userSaveRequestDto.getPassword());

        assertNotNull(userEntity);
        assertEquals(userSaveRequestDto.getUsername(), userEntity.getUsername());
        assertEquals(userSaveRequestDto.getLoginId(), userEntity.getLoginId());
        assertEquals(userSaveRequestDto.getPassword(), userEntity.getPassword());
        assertEquals(userSaveRequestDto.getEmail(), userEntity.getEmail());
        assertEquals(userSaveRequestDto.getNickName(), userEntity.getNickName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();

        assertNotNull(userSaveRequestDto);
        assertNull(userSaveRequestDto.getUsername());
        assertNull(userSaveRequestDto.getLoginId());
        assertNull(userSaveRequestDto.getPassword());
        assertNull(userSaveRequestDto.getEmail());
        assertNull(userSaveRequestDto.getNickName());
    }

    @Test
    @DisplayName("이름 유효성 검증 테스트")
    void username_validation() {
        userRequestVO.setUsername(INVALID_EMPTY);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("로그인 아이디 유효성 검증 테스트")
    void loginId_validation() {
        userRequestVO.setLoginId(INVALID_EMPTY);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("비밀번호 유효성 검증 테스트")
    void password_validation() {
        userRequestVO.setPassword(INVALID_EMPTY);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("이메일 유효성 검증 테스트")
    void email_validation() {
        userRequestVO.setEmail(INVALID_EMAIL);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("닉네임 유효성 검증 테스트")
    void nickName_validation() {
        userRequestVO.setNickName(INVALID_EMPTY);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("목표 유효성 검증 테스트")
    void goalMsg_validation() {
        userRequestVO.setGoalMsg(INVALID_EMPTY);
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .userRequestVO(userRequestVO)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }
}