package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoDto;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.except.DuplicateEmailException;
import com.freesia.imyourfreesia.except.InvalidPasswordException;
import com.freesia.imyourfreesia.except.UserNotActivatedException;
import com.freesia.imyourfreesia.jwt.JwtTokenProvider;
import com.freesia.imyourfreesia.service.file.FileHandler;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final GoalMsgRepository goalMsgRepository;

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;
    private final OAuth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponseDto socialLogin(String accessToken, SocialProvider provider, HttpServletResponse response) {
        User user = saveSocialUser(accessToken, provider);
        verifyUserActivation(user);
        return issueToken(user, response);
    }

    @Override
    public String sendAuthEmail(String email) throws Exception {
        checkUserExistence(email);
        return emailService.sendAuthMail(email);
    }

    @Override
    public User generalJoin(UserSaveRequestDto requestDto, MultipartFile profileImage) throws Exception {
        checkUserExistence(requestDto.getEmail());
        User user = requestDto.toEntity();
        user.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
        user.updateProfileImg(saveProfileImage(profileImage));
        addGoalMsg(user, requestDto.getGoalMsg());
        return userRepository.save(user);
    }

    @Override
    public TokenResponseDto generalLogin(String loginId, String password, HttpServletResponse response) {
        User user = userRepository.findByLoginId(loginId);
        passwordAuthenticate(user, password);
        verifyUserActivation(user);
        return issueToken(user, response);
    }

    @Override
    public String saveProfileImage(MultipartFile profileImage) throws Exception {
        return fileHandler.saveProfile(profileImage).getFilePath();
    }

    private User saveSocialUser(String accessToken, SocialProvider provider) {
        String userInfoUrl = SocialProvider.findProviderInfoUrl(provider);
        OAuth2UserInfoDto oAuth2UserInfoDto = oAuth2Service.getUserInfoByAccessToken(accessToken, userInfoUrl);
        return findOrAddUser(oAuth2UserInfoDto);
    }

    private User findOrAddUser(OAuth2UserInfoDto oAuth2UserInfoDto) {
        if (userRepository.existsByEmail(oAuth2UserInfoDto.getEmail())) {
            return userRepository.findByEmail(oAuth2UserInfoDto.getEmail());
        }
        return userRepository.save(User.builder()
                .username(oAuth2UserInfoDto.getName())
                .email(oAuth2UserInfoDto.getEmail())
                .build());
    }

    private void checkUserExistence(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    private void passwordAuthenticate(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

    private void verifyUserActivation(User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException();
        }
    }

    private void addGoalMsg(User user, String message) {
        GoalMsg goalMsg = GoalMsg.builder()
                .goalMsg(message)
                .build();
        goalMsg.setUser(user);
        goalMsgRepository.save(goalMsg);
    }

    private TokenResponseDto issueToken(User user, HttpServletResponse response) {
        String token = jwtTokenProvider.generateToken(user.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, token);
        return new TokenResponseDto(user, token);
    }
}