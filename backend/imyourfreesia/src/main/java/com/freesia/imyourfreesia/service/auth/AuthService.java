package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.config.JwtTokenUtil;
import com.freesia.imyourfreesia.domain.user.Authority;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final GoogleService googleService;
    private final KakaoService kakaoService;
    private final NaverService naverService;
    private final CustomUserDetailsService userDetailsService;

    // 구글 로그인
    @Transactional
    public String googleLogin(GoogleLoginReqDto googleLoginReqDto, MultipartFile profileImage) throws Exception {

        String jwt;

        GoogleOAuth2UserInfoDto googleOAuth2UserInfoDto = googleService.getUserInfoByAccessToken(googleLoginReqDto.getAccessToken());

        if (userRepository.findOneWithAuthoritiesByEmail(googleOAuth2UserInfoDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        String filePath = convertImage(profileImage);

        User user = User.builder()
                .username(googleOAuth2UserInfoDto.getName())
                .email(googleOAuth2UserInfoDto.getEmail())
                .nickname(googleLoginReqDto.getNickName())
                .profileImg(filePath)
                .goalMsg(googleLoginReqDto.getGoalMsg())
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(googleOAuth2UserInfoDto.getEmail());

        jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;
    }

    // 카카오 로그인
    @Transactional
    public String kakaoLogin(KakaoLoginReqDto kakaoLoginReqDto, MultipartFile profileImage) throws Exception {

        String jwt;

        KakaoOAuth2UserInfoDto kakaoOAuth2UserInfoDto = kakaoService.getUserInfoByAccessToken(kakaoLoginReqDto.getAccessToken());

        if (userRepository.findOneWithAuthoritiesByEmail(kakaoOAuth2UserInfoDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        String filePath = convertImage(profileImage);

        User user = User.builder()
                .username(kakaoOAuth2UserInfoDto.getName())
                .email(kakaoOAuth2UserInfoDto.getEmail())
                .nickname(kakaoLoginReqDto.getNickName())
                .profileImg(filePath)
                .goalMsg(kakaoLoginReqDto.getGoalMsg())
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(kakaoOAuth2UserInfoDto.getEmail());

        jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;
    }

    // 네이버 로그인
    @Transactional
    public String naverLoin(NaverLoginReqDto naverLoginReqDto, MultipartFile profileImage) throws Exception {

        String jwt;

        NaverOAuth2UserInfoDto naverOAuth2UserInfoDto = naverService.getUserInfoByAccessToken(naverLoginReqDto.getAccessToken());

        if (userRepository.findOneWithAuthoritiesByEmail(naverOAuth2UserInfoDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        String filePath = convertImage(profileImage);

        User user = User.builder()
                .username(naverOAuth2UserInfoDto.getName())
                .email(naverOAuth2UserInfoDto.getEmail())
                .nickname(naverLoginReqDto.getNickName())
                .profileImg(filePath)
                .goalMsg(naverLoginReqDto.getGoalMsg())
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(naverOAuth2UserInfoDto.getEmail());

        jwt = jwtTokenUtil.generateToken(userDetails);
        return jwt;


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
                .password(userSaveRequestDto.getPassword())
                .email(userSaveRequestDto.getEmail())
                .nickname(userSaveRequestDto.getNickName())
                .profileImg(filePath)
                .goalMsg(userSaveRequestDto.getGoalMsg())
                .activated(true)
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(user);
    }


    // 일반 로그인
    public String generalLogin(String loginId, String password) {

        String jwt;

        User user = userRepository.findByLoginIdAndPassword(loginId, password);

        if(userRepository.findByLoginIdAndPassword(loginId, password) != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            jwt = jwtTokenUtil.generateToken(userDetails);
            return jwt;

        }

        return "회원 정보가 없습니다.";
    }

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

                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            String originalFileExtension = "";
            String contentType = profileImage.getContentType();

            if (ObjectUtils.isEmpty(contentType)) {
                return null;
            } else {
                if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";
                else if (contentType.contains("image/png"))
                    originalFileExtension = ".png";
                else
                    return null;
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
