package com.freesia.imyourfreesia.dto.community;

import static com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVOTest.FILE_BYTE;
import static com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVOTest.FILE_NAME;
import static com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVOTest.FILE_ORIGINAL_NAME;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import java.util.Collections;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class CommunityRequestVOTest {
    public static CommunityRequestVO testCommunityRequestVO(Community community, User user) {
        MockMultipartFile file = new MockMultipartFile(FILE_NAME, FILE_ORIGINAL_NAME, MediaType.IMAGE_JPEG_VALUE, FILE_BYTE.getBytes());
        return CommunityRequestVO.builder()
                .email(user.getEmail())
                .title(community.getTitle())
                .content(community.getContent())
                .category(community.getCategory().getCategoryName())
                .files(Collections.singletonList(file))
                .build();
    }
}