package com.freesia.imyourfreesia.service.user;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.auth.AuthService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GoalMsgRepository goalMsgRepository;

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponseDto findUserDetailsByEmail(String email) {
        User user = findUserByEmail(email);
        GoalMsg goalMsg = goalMsgRepository.findByUser(user);
        return new UserResponseDto(user, goalMsg);

    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<User> findUserList() {
        return userRepository.findAll();
    }

    @Override
    public UserResponseDto update(String email, UserUpdateRequestDto updateRequestDto, GoalMsgUpdateRequestDto msgRequestDto, MultipartFile file) throws Exception {
        User user = findUserByEmail(email);
        updateProfile(user, updateRequestDto, file);
        GoalMsg goalMsg = updateOrAddGoalMsg(user, msgRequestDto.getGoalMsg());
        return new UserResponseDto(user, goalMsg);
    }

    @Override
    public UserResponseDto updatePw(String email, UserPasswordUpdateRequestDto requestDto) {
        User user = findUserByEmail(email);
        user.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
        GoalMsg goalMsg = goalMsgRepository.findByUser(user);
        return new UserResponseDto(user, goalMsg);
    }

    @Override
    public String getUserProfileByteArray(String email) throws IOException {
        User user = findUserByEmail(email);
        InputStream imageStream = new FileInputStream(user.getProfileImg());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    private void updateProfile(User user, UserUpdateRequestDto updateRequestDto, MultipartFile files) throws Exception {
        String profileImg = authService.saveProfileImage(files);
        user.updateProfile(updateRequestDto.getNickName(), profileImg);
    }

    private GoalMsg updateOrAddGoalMsg(User user, String message) {
        if (goalMsgRepository.existsByUser(user)) {
            GoalMsg goalMsg = goalMsgRepository.findByUser(user);
            goalMsg.update(message);
            return goalMsg;
        }
        return authService.saveGoalMsg(user, message);
    }
}