package com.freesia.imyourfreesia.controller;

import static com.freesia.imyourfreesia.domain.user.UserTest.USER_PASSWORD;
import static com.freesia.imyourfreesia.service.auth.AuthServiceImplTest.AUTH_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.auth.OAuth2LoginRequestDto;
import com.freesia.imyourfreesia.dto.auth.OAuth2LoginRequestDtoTest;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDtoTest;
import com.freesia.imyourfreesia.dto.auth.UserRequestVO;
import com.freesia.imyourfreesia.dto.auth.UserRequestVOTest;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.auth.AuthService;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@WebMvcTest(controllers = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class AuthControllerTest {
    private User user;
    private GoalMsg goalMsg;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        user = UserTest.testUser();
        goalMsg = GoalMsgTest.testGoalMsg();
        goalMsg.setUser(user);
    }

    @Test
    @DisplayName("애플리케이션 상태 검사 테스트")
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/healthCheck"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("구글 로그인 테스트")
    void testGoogleLogin() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.testTokenResponseDto(user);
        when(authService.loginWithSocial(anyString(), any(), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        OAuth2LoginRequestDto oAuth2LoginRequestDto = OAuth2LoginRequestDtoTest.testOAuth2LoginRequestDto();
        mockMvc.perform(post("/googleLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(oAuth2LoginRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).loginWithSocial(anyString(), any(), any(HttpServletResponse.class));
    }

    @Test
    @DisplayName("카카오 로그인 테스트")
    void testKakaoLogin() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.testTokenResponseDto(user);
        when(authService.loginWithSocial(anyString(), any(), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        OAuth2LoginRequestDto oAuth2LoginRequestDto = OAuth2LoginRequestDtoTest.testOAuth2LoginRequestDto();
        mockMvc.perform(post("/kakaoLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(oAuth2LoginRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).loginWithSocial(anyString(), any(), any(HttpServletResponse.class));
    }

    @Test
    @DisplayName("네이버 로그인 테스트")
    void testNaverLogin() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.testTokenResponseDto(user);
        when(authService.loginWithSocial(anyString(), any(), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        OAuth2LoginRequestDto oAuth2LoginRequestDto = OAuth2LoginRequestDtoTest.testOAuth2LoginRequestDto();
        mockMvc.perform(post("/naverLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(oAuth2LoginRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).loginWithSocial(anyString(), any(), any(HttpServletResponse.class));
    }

    @Test
    @DisplayName("회원 가입 시 이메일 인증 코드 전송 테스트")
    void testEmailAuth() throws Exception {
        when(authService.sendAuthEmail(anyString())).thenReturn(AUTH_CODE);

        mockMvc.perform(post("/emailAuth")
                        .param("email", user.getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).sendAuthEmail(anyString());
    }

    @Test
    @DisplayName("일반 회원 가입 테스트")
    void testRegister() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user, goalMsg);
        when(authService.register(any(), any(MultipartFile.class))).thenReturn(userResponseDto);

        UserRequestVO userRequestVO = UserRequestVOTest.testUserRequestVO(user, goalMsg);
        mockMvc.perform(multipart("/register")
                        .file((MockMultipartFile) userRequestVO.getProfileImg())
                        .param("username", userRequestVO.getUsername())
                        .param("loginId", userRequestVO.getLoginId())
                        .param("password", userRequestVO.getPassword())
                        .param("email", userRequestVO.getEmail())
                        .param("nickName", userRequestVO.getNickName())
                        .param("goalMsg", userRequestVO.getGoalMsg())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).register(any(), any(MultipartFile.class));
    }

    @Test
    @DisplayName("일반 로그인 테스트")
    void testLogin() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.testTokenResponseDto(user);
        when(authService.login(anyString(), anyString(), any(HttpServletResponse.class))).thenReturn(tokenResponseDto);

        mockMvc.perform(post("/login")
                        .param("loginId", user.getLoginId())
                        .param("password", USER_PASSWORD)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
}