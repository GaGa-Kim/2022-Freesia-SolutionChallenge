package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.domain.user.Authority;
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
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // 구글 로그인
    @Transactional
    public TokenDto googleLogin(String accessToken, HttpServletResponse response) {

        String jwt;

        GoogleOAuth2UserInfoDto googleOAuth2UserInfoDto = googleService.getUserInfoByAccessToken(accessToken);
        if (userRepository.findOneWithAuthoritiesByEmail(googleOAuth2UserInfoDto.getEmail()).orElse(null) == null) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            User user = User.builder()
                    .username(googleOAuth2UserInfoDto.getName())
                    .email(googleOAuth2UserInfoDto.getEmail())
                    .activated(true)
                    .authorities(Collections.singleton(authority))
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

        if (userRepository.findOneWithAuthoritiesByEmail(kakaoOAuth2UserInfoDto.getEmail()).orElse(null) == null) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            User user = User.builder()
                    .username(kakaoOAuth2UserInfoDto.getName())
                    .email(kakaoOAuth2UserInfoDto.getEmail())
                    .activated(true)
                    .authorities(Collections.singleton(authority))
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

        if (userRepository.findOneWithAuthoritiesByEmail(naverOAuth2UserInfoDto.getEmail()).orElse(null) == null) {
            Authority authority = Authority.builder()
                    .authorityName("ROLE_USER")
                    .build();

            User user = User.builder()
                    .username(naverOAuth2UserInfoDto.getName())
                    .email(naverOAuth2UserInfoDto.getEmail())
                    .activated(true)
                    .authorities(Collections.singleton(authority))
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

        if (userRepository.findOneWithAuthoritiesByEmail(userSaveRequestDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        String filePath = convertImage(profileImage);

        User user = User.builder()
                .username(userSaveRequestDto.getUsername())
                .loginId(userSaveRequestDto.getLoginId())
                .password(passwordEncoder.encode(userSaveRequestDto.getPassword()))
                .email(userSaveRequestDto.getEmail())
                .nickName(userSaveRequestDto.getNickName())
                .profileImg(filePath)
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        GoalMsg goalMsg = GoalMsg.builder()
                .goalMsg(userSaveRequestDto.getGoalMsg())
                .build();

        goalMsg.setUserId(user);

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

        String filePath = "";

        if (profileImage != null) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + current_date;
            File file = new File(path);

            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                if (!wasSuccessful) {
                    System.out.println("file: was not successful");
                }
            }

            String originalFileExtension = "";
            String contentType = profileImage.getContentType();

            if (ObjectUtils.isEmpty(contentType)) {
                return null;
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    return null;
                }
            }

            String new_file_name = System.nanoTime() + originalFileExtension;

            filePath = absolutePath + path + File.separator + new_file_name;

            file = new File(absolutePath + path + File.separator + new_file_name);
            profileImage.transferTo(file);

            file.setWritable(true);
            file.setReadable(true);

        }

        return filePath;
    }

}
