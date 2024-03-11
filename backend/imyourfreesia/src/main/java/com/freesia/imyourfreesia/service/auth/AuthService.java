package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.auth.GoogleOAuth2UserInfoDto;
import com.freesia.imyourfreesia.dto.auth.KakaoOAuth2UserInfoDto;
import com.freesia.imyourfreesia.dto.auth.NaverOAuth2UserInfoDto;
import com.freesia.imyourfreesia.dto.auth.TokenDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.jwt.JwtTokenProvider;
import com.freesia.imyourfreesia.service.file.FileHandler;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final GoalMsgRepository goalMsgRepository;
    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;

    // 구글 로그인
    @Transactional
    public TokenDto googleLogin(String accessToken, HttpServletResponse response) {

        String jwt;

        GoogleOAuth2UserInfoDto googleOAuth2UserInfoDto = googleService.getUserInfoByAccessToken(accessToken);
        if (!userRepository.existsByEmail(googleOAuth2UserInfoDto.getEmail())) {
            User user = User.builder()
                    .username(googleOAuth2UserInfoDto.getName())
                    .email(googleOAuth2UserInfoDto.getEmail())
                    .build();

            userRepository.save(user);
        }
        jwt = jwtTokenProvider.generateToken(googleOAuth2UserInfoDto.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, jwt);
        return new TokenDto(jwt, googleOAuth2UserInfoDto.getEmail());
    }

    // 카카오 로그인
    @Transactional
    public TokenDto kakaoLogin(String accessToken, HttpServletResponse response) {

        String jwt;

        KakaoOAuth2UserInfoDto kakaoOAuth2UserInfoDto = kakaoService.getUserInfoByAccessToken(accessToken);

        if (!userRepository.existsByEmail(kakaoOAuth2UserInfoDto.getEmail())) {
            User user = User.builder()
                    .username(kakaoOAuth2UserInfoDto.getName())
                    .email(kakaoOAuth2UserInfoDto.getEmail())
                    .build();

            userRepository.save(user);
        }
        jwt = jwtTokenProvider.generateToken(kakaoOAuth2UserInfoDto.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, jwt);
        return new TokenDto(jwt, kakaoOAuth2UserInfoDto.getEmail());
    }

    // 네이버 로그인
    @Transactional
    public TokenDto naverLoin(String accessToken, HttpServletResponse response) {

        String jwt;

        NaverOAuth2UserInfoDto naverOAuth2UserInfoDto = naverService.getUserInfoByAccessToken(accessToken);

        if (!userRepository.existsByEmail(naverOAuth2UserInfoDto.getEmail())) {
            User user = User.builder()
                    .username(naverOAuth2UserInfoDto.getName())
                    .email(naverOAuth2UserInfoDto.getEmail())
                    .build();

            userRepository.save(user);
        }
        jwt = jwtTokenProvider.generateToken(naverOAuth2UserInfoDto.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, jwt);
        return new TokenDto(jwt, naverOAuth2UserInfoDto.getEmail());
    }

    // 일반 회원가입
    @Transactional
    public User generalJoin(UserSaveRequestDto userSaveRequestDto, MultipartFile profileImage) throws Exception {

        if (userRepository.existsByEmail(userSaveRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        String filePath = convertImage(profileImage);

        User user = User.builder()
                .username(userSaveRequestDto.getUsername())
                .loginId(userSaveRequestDto.getLoginId())
                .password(passwordEncoder.encode(userSaveRequestDto.getPassword()))
                .email(userSaveRequestDto.getEmail())
                .nickName(userSaveRequestDto.getNickName())
                .profileImg(filePath)
                .build();

        GoalMsg goalMsg = GoalMsg.builder()
                .goalMsg(userSaveRequestDto.getGoalMsg())
                .build();

        goalMsg.setUser(user);

        goalMsgRepository.save(goalMsg);

        return userRepository.save(user);
    }

    // 일반 로그인
    public TokenDto generalLogin(String loginId, String password, HttpServletResponse response) {

        String jwt;

        User user = userRepository.findByLoginId(loginId);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("로그인에 실패했습니다.");
        }
        jwt = jwtTokenProvider.generateToken(user.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, jwt);
        return new TokenDto(jwt, user.getEmail());
    }

    // 사진 변환
    public String convertImage(MultipartFile profileImage) throws Exception {

        return fileHandler.saveProfile(profileImage).getFilePath();
    }

}
