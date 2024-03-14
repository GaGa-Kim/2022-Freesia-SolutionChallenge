package com.freesia.imyourfreesia.service.auth;

import static com.freesia.imyourfreesia.dto.auth.OAuth2LoginRequestDtoTest.SOCIAL_ACCESS_TOKEN;
import static com.freesia.imyourfreesia.dto.auth.TokenResponseDtoTest.TOKEN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDtoTest;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.except.DuplicateEmailException;
import com.freesia.imyourfreesia.except.InvalidPasswordException;
import com.freesia.imyourfreesia.except.UserNotActivatedException;
import com.freesia.imyourfreesia.jwt.JwtTokenProvider;
import com.freesia.imyourfreesia.service.file.FileHandler;
import javax.servlet.http.HttpServletResponse;
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
class AuthServiceImplTest {
    public static final String AUTH_CODE = "auth";
    private User user;
    private GoalMsg goalMsg;

    @Mock
    private UserRepository userRepository;
    @Mock
    private GoalMsgRepository goalMsgRepository;
    @Mock
    private OAuth2Service oAuth2Service;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private FileHandler fileHandler;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(user);
    }

    @Test
    @DisplayName("소셜 회원 가입 테스트")
    void testRegisterWithSocial() {
        when(oAuth2Service.getUserInfoByAccessToken(anyString(), anyString())).thenReturn(OAuth2UserInfoRequestDtoTest.testOAuth2UserInfoRequestDto(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyString())).thenReturn(TOKEN);
        doNothing().when(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());

        TokenResponseDto result = authService.loginWithSocial(SOCIAL_ACCESS_TOKEN, SocialProvider.GOOGLE, mock(HttpServletResponse.class));

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(TOKEN, result.getToken());

        verify(oAuth2Service).getUserInfoByAccessToken(anyString(), anyString());
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).save(any(User.class));
        verify(jwtTokenProvider).generateToken(anyString());
        verify(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());
    }

    @Test
    @DisplayName("소셜 로그인 테스트")
    void testLoginWithSocial() {
        when(oAuth2Service.getUserInfoByAccessToken(anyString(), anyString())).thenReturn(OAuth2UserInfoRequestDtoTest.testOAuth2UserInfoRequestDto(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyString())).thenReturn(TOKEN);
        doNothing().when(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());

        TokenResponseDto result = authService.loginWithSocial(SOCIAL_ACCESS_TOKEN, SocialProvider.GOOGLE, mock(HttpServletResponse.class));

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(TOKEN, result.getToken());

        verify(oAuth2Service).getUserInfoByAccessToken(anyString(), anyString());
        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).findByEmail(anyString());
        verify(jwtTokenProvider).generateToken(anyString());
        verify(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());
    }

    @Test
    @DisplayName("이메일 인증 코드 테스트")
    void testSendAuthEmail() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(emailService.sendAuthenticationEmail(anyString())).thenReturn(AUTH_CODE);

        String result = authService.sendAuthEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(AUTH_CODE, result);

        verify(userRepository).existsByEmail(anyString());
        verify(emailService).sendAuthenticationEmail(anyString());
    }

    @Test
    @DisplayName("일반 회원 가입 테스트")
    void testRegister() throws Exception {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        when(fileHandler.saveProfile(any(MultipartFile.class))).thenReturn(user.getProfileImg());
        when(goalMsgRepository.save(any(GoalMsg.class))).thenReturn(goalMsg);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.testUserSaveRequestDto(UserRequestVOTest.testUserRequestVO(user, goalMsg));
        UserResponseDto result = authService.register(userSaveRequestDto, mock(MultipartFile.class));

        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getLoginId(), result.getLoginId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickName(), result.getNickName());
        assertEquals(goalMsg.getGoalMsg(), result.getGoalMsg());

        verify(userRepository).existsByEmail(anyString());
        verify(passwordEncoder).encode(anyString());
        verify(fileHandler).saveProfile(any(MultipartFile.class));
        verify(goalMsgRepository).save(any(GoalMsg.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("이미 존재하는 이메일을 가지고 일반 회원 가입 테스트")
    void testRegisterWithExistEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.testUserSaveRequestDto(UserRequestVOTest.testUserRequestVO(user, goalMsg));

        assertThatThrownBy(() -> authService.register(userSaveRequestDto, mock(MultipartFile.class)))
                .isInstanceOf(DuplicateEmailException.class);

        verify(userRepository).existsByEmail(anyString());
    }

    @Test
    @DisplayName("일반 로그인 테스트")
    void testLogin() {
        when(userRepository.findByLoginId(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(anyString())).thenReturn(TOKEN);
        doNothing().when(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());

        TokenResponseDto result = authService.login(user.getLoginId(), user.getPassword(), mock(HttpServletResponse.class));

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(TOKEN, result.getToken());

        verify(userRepository).findByLoginId(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
        verify(jwtTokenProvider).generateToken(anyString());
        verify(jwtTokenProvider).setHeaderAccessToken(any(HttpServletResponse.class), anyString());
    }

    @Test
    @DisplayName("잘못된 비밀번호를 가지고 일반 로그인 테스트")
    void testLoginWithInvalidPassword() {
        when(userRepository.findByLoginId(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(user.getLoginId(), user.getPassword(), mock(HttpServletResponse.class)))
                .isInstanceOf(InvalidPasswordException.class);

        verify(userRepository).findByLoginId(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("비활성화된 회원 일반 로그인 테스트")
    void testLoginWithNotActivate() {
        when(userRepository.findByLoginId(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        user.setActivated(false);
        assertThatThrownBy(() -> authService.login(user.getLoginId(), user.getPassword(), mock(HttpServletResponse.class)))
                .isInstanceOf(UserNotActivatedException.class);

        verify(userRepository).findByLoginId(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("회원 프로필 이미지 저장 테스트")
    void testSaveProfileImage() throws Exception {
        when(fileHandler.saveProfile(any(MultipartFile.class))).thenReturn(user.getProfileImg());

        String result = authService.saveProfileImage(mock(MultipartFile.class));

        assertNotNull(result);

        verify(fileHandler).saveProfile(any());
    }

    @Test
    @DisplayName("회원 목표 저장 테스트")
    void testSaveGoalMsg() {
        when(goalMsgRepository.save(any(GoalMsg.class))).thenReturn(goalMsg);

        GoalMsg result = authService.saveGoalMsg(user, goalMsg.getGoalMsg());

        assertNotNull(result);
        assertEquals(goalMsg.getUser(), result.getUser());
        assertEquals(goalMsg.getGoalMsg(), result.getGoalMsg());

        verify(goalMsgRepository).save(any(GoalMsg.class));
    }
}