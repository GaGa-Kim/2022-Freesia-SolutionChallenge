package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Validated
public interface AuthService {
    /**
     * 소셜 회원 가입 또는 로그인을 한 후, JWT 토큰을 발급 받고 회원 정보를 조회한다.
     *
     * @param accessToken (소셜 회원 정보 조회를 위한 액세스 토큰)
     * @param provider    (소셜 로그인 공급자 - 구글, 카카오, 네이버)
     * @param response    (HttpServletResponse)
     * @return TokenResponseDto (JWT 토큰과 회원 정보를 담은 DTO)
     */
    TokenResponseDto loginWithSocial(String accessToken, SocialProvider provider, HttpServletResponse response);

    /**
     * 일반 회원 가입 시 이메일 인증 코드를 전송한다.
     *
     * @param email (회원 이메일)
     * @return String (이메일 인증 코드)
     */
    String sendAuthEmail(String email) throws Exception;

    /**
     * 일반 회원 가입을 한 후, 회원 정보를 조회한다.
     *
     * @param requestDto   (회원 저장 정보를 담은 DTO)
     * @param profileImage (프로필 이미지)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    UserResponseDto register(@Valid UserSaveRequestDto requestDto, MultipartFile profileImage) throws Exception;

    /**
     * 일반 로그인을 한 후, JWT 토큰을 발급 받고 회원 정보를 조회한다.
     *
     * @param loginId  (로그인 아이디)
     * @param password (로그인 비밀번호)
     * @param response (HttpServletResponse)
     * @return TokenResponseDto (JWT 토큰과 회원 정보를 담은 DTO)
     */
    TokenResponseDto login(String loginId, String password, HttpServletResponse response);

    /**
     * 회원 프로필 이미지를 저장한다.
     *
     * @param profileImage (프로필 이미지)
     * @return String (프로필 이미지가 저장된 경로)
     */
    String saveProfileImage(MultipartFile profileImage) throws Exception;

    /**
     * 회원 목표를 저장한다.
     *
     * @param user    (회원)
     * @param message (목표 메시지)
     * @return GoalMsg (회원 목표)
     */
    GoalMsg saveGoalMsg(User user, String message);
}
