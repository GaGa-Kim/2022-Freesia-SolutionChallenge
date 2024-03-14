package com.freesia.imyourfreesia.service.user;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDtoTest;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDtoTest;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDtoTest;
import com.freesia.imyourfreesia.service.auth.AuthService;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private User user;
    private GoalMsg goalMsg;

    @Mock
    private UserRepository userRepository;
    @Mock
    private GoalMsgRepository goalMsgRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(user);
    }

    @Test
    @DisplayName("회원 이메일로 회원 조회 테스트")
    void testFindUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User result = userService.findUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(user.getProfileImg(), result.getProfileImg());
        assertEquals(user.getRole(), result.getRole());
        assertTrue(result.isActivated());

        verify(userRepository).findByEmail(anyString());
    }

    @Test
    @DisplayName("회원 이메일로 회원 상세 조회 테스트")
    void testGetUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(goalMsgRepository.findByUser(any())).thenReturn(goalMsg);

        UserResponseDto result = userService.getUserByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(Period.between(goalMsg.getModifiedDate(), LocalDate.now()).getDays() + 1, result.getDays());
        assertEquals(goalMsg.getGoalMsg(), result.getGoalMsg());

        verify(userRepository).findByEmail(anyString());
        verify(goalMsgRepository).findByUser(any());
    }

    @Test
    @DisplayName("회원 아이디로 회원 조회 테스트")
    void testFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(user.getProfileImg(), result.getProfileImg());
        assertEquals(user.getRole(), result.getRole());
        assertTrue(result.isActivated());

        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("회원 전체 목록 조회 테스트")
    void testFindUserList() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<User> result = userService.findUserList();

        assertNotNull(result);
        assertEquals(user.getUsername(), result.get(0).getUsername());
        assertEquals(user.getLoginId(), result.get(0).getLoginId());
        assertEquals(user.getEmail(), result.get(0).getEmail());
        assertEquals(user.getPassword(), result.get(0).getPassword());
        assertEquals(user.getNickName(), result.get(0).getNickName());
        assertEquals(user.getProfileImg(), result.get(0).getProfileImg());
        assertEquals(user.getRole(), result.get(0).getRole());
        assertTrue(result.get(0).isActivated());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("회원 프로필 수정 테스트")
    void testUpdate() throws Exception {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(authService.saveProfileImage(any(MultipartFile.class))).thenReturn(user.getProfileImg());
        when(goalMsgRepository.existsByUser(any())).thenReturn(true);
        when(goalMsgRepository.findByUser(any())).thenReturn(goalMsg);

        UserRequestVO userRequestVO = UserRequestVOTest.testUserRequestVO(user, goalMsg);
        UserUpdateRequestDto updateRequestDto = UserUpdateRequestDtoTest.testUserUpdateRequestDto(userRequestVO);
        GoalMsgUpdateRequestDto goalMsgUpdateRequestDto = GoalMsgUpdateRequestDtoTest.testGoalMsgUpdateRequestDto(userRequestVO);
        UserResponseDto result = userService.update(user.getEmail(), updateRequestDto, goalMsgUpdateRequestDto, mock(MultipartFile.class));

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(Period.between(goalMsg.getModifiedDate(), LocalDate.now()).getDays() + 1, result.getDays());
        assertEquals(goalMsg.getGoalMsg(), result.getGoalMsg());

        verify(authService).saveProfileImage(any(MultipartFile.class));
        verify(goalMsgRepository).existsByUser(any());
        verify(goalMsgRepository).findByUser(any());
    }

    @Test
    @DisplayName("회원 비밀번호 수정 테스트")
    void testUpdatePw() {
        when(userService.findUserByEmail(anyString())).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(goalMsgRepository.findByUser(any())).thenReturn(goalMsg);

        UserPasswordUpdateRequestDto updateRequestDto = UserPasswordUpdateRequestDtoTest.testUserPasswordUpdateRequestDto(user);
        UserResponseDto result = userService.updatePw(user.getEmail(), updateRequestDto);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(Period.between(goalMsg.getModifiedDate(), LocalDate.now()).getDays() + 1, result.getDays());
        assertEquals(goalMsg.getGoalMsg(), result.getGoalMsg());

        verify(goalMsgRepository).findByUser(any());
    }
}