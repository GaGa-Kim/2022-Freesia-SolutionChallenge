package com.freesia.imyourfreesia.dto.auth;

import com.freesia.imyourfreesia.domain.user.GoalMsg;
import com.freesia.imyourfreesia.domain.user.User;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class UserRequestVOTest {
    public static final String PROFILE_IMG_NAME = "profileImg";
    public static final String PROFILE_IMG_ORIGINAL_NAME = "profile.jpg";
    public static final String PROFILE_BYTE = "profile.jpg";

    public static UserRequestVO testUserRequestVO(User user, GoalMsg goalMsg) {
        MockMultipartFile profileImg = new MockMultipartFile(PROFILE_IMG_NAME, PROFILE_IMG_ORIGINAL_NAME, MediaType.IMAGE_JPEG_VALUE, PROFILE_BYTE.getBytes());
        return UserRequestVO.builder()
                .username(user.getUsername())
                .loginId(user.getLoginId())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .profileImg(profileImg)
                .goalMsg(goalMsg.getGoalMsg())
                .build();
    }
}