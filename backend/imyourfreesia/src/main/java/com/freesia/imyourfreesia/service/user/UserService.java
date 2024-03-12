package com.freesia.imyourfreesia.service.user;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.GoalMsgRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.user.GoalMsgUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserPasswordUpdateRequestDto;
import com.freesia.imyourfreesia.dto.user.UserResponseDto;
import com.freesia.imyourfreesia.dto.user.UserUpdateRequestDto;
import com.freesia.imyourfreesia.service.auth.AuthServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final GoalMsgRepository goalMsgRepository;
    private final AuthServiceImpl authServiceImpl;
    private final PasswordEncoder passwordEncoder;

    /* 유저 정보 조회 */
    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        GoalMsg goalMsg = goalMsgRepository.findByUser(user);

        return new UserResponseDto(user, goalMsg);

    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
    }

    public List<User> findUserList() {
        return userRepository.findAll();
    }

    /* 유저 정보 수정 */
    @Transactional
    public Long update(String email, UserUpdateRequestDto userUpdateRequestDto,
                       GoalMsgUpdateRequestDto goalMsgUpdateRequestDto, MultipartFile files) throws Exception {
        User user = userRepository.findByEmail(email);
        String profileImg = authServiceImpl.saveProfileImage(files);

        user.updateProfile(userUpdateRequestDto.getNickName(), profileImg);

        if (goalMsgRepository.findByUser(user) != null) {
            GoalMsg goalMsg = goalMsgRepository.findByUser(user);
            goalMsg.update(goalMsgUpdateRequestDto.getGoalMsg());
        } else {
            GoalMsg goalMsg = GoalMsg.builder()
                    .goalMsg(goalMsgUpdateRequestDto.getGoalMsg())
                    .build();

            goalMsg.setUser(user);
            goalMsgRepository.save(goalMsg);
        }
        return user.getId();
    }

    /* 유저 비밀번호 수정 */
    @Transactional
    public Long updatePw(String email, UserPasswordUpdateRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(email);

        user.updatePassword(passwordEncoder.encode(requestDto.getPassword()));

        return user.getId();
    }
}

