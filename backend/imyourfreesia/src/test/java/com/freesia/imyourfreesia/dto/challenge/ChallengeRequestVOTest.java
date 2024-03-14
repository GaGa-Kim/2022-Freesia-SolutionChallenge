package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.Collections;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class ChallengeRequestVOTest {
    public static final String FILE_NAME = "file";
    public static final String FILE_ORIGINAL_NAME = "file.jpg";
    public static final String FILE_BYTE = "file.jpg";

    public static ChallengeRequestVO testChallengeRequestVO(Challenge challenge, User user) {
        MockMultipartFile file = new MockMultipartFile(FILE_NAME, FILE_ORIGINAL_NAME, MediaType.IMAGE_JPEG_VALUE, FILE_BYTE.getBytes());
        return ChallengeRequestVO.builder()
                .userId(user.getId())
                .title(challenge.getTitle())
                .content(challenge.getContent())
                .files(Collections.singletonList(file))
                .build();
    }
}