package com.freesia.imyourfreesia.service.auth;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.SocialProvider;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDto;
import com.freesia.imyourfreesia.dto.auth.TokenResponseDto;
import com.freesia.imyourfreesia.dto.auth.UserSaveRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
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
    public UserResponseDto generalJoin(UserSaveRequestDto requestDto, MultipartFile profileImage) throws Exception {
        checkUserExistence(requestDto.getEmail());
        User user = requestDto.toEntity();
        user.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
        user.updateProfileImg(saveProfileImage(profileImage));
        GoalMsg goalMsg = addGoalMsg(user, requestDto.getGoalMsg());
        userRepository.save(user);
        return new UserResponseDto(user, goalMsg);
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
        OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto = oAuth2Service.getUserInfoByAccessToken(accessToken, userInfoUrl);
        return findOrAddUser(oAuth2UserInfoRequestDto);
    }

    private User findOrAddUser(OAuth2UserInfoRequestDto oAuth2UserInfoRequestDto) {
        if (userRepository.existsByEmail(oAuth2UserInfoRequestDto.getEmail())) {
            return userRepository.findByEmail(oAuth2UserInfoRequestDto.getEmail());
        }
        return userRepository.save(User.builder()
                .username(oAuth2UserInfoRequestDto.getName())
                .email(oAuth2UserInfoRequestDto.getEmail())
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

    private GoalMsg addGoalMsg(User user, String message) {
        GoalMsg goalMsg = GoalMsg.builder()
                .goalMsg(message)
                .build();
        goalMsg.setUser(user);
        goalMsgRepository.save(goalMsg);
        return goalMsg;
    }

    private TokenResponseDto issueToken(User user, HttpServletResponse response) {
        String token = jwtTokenProvider.generateToken(user.getEmail());
        jwtTokenProvider.setHeaderAccessToken(response, token);
        return new TokenResponseDto(user, token);
    }
}