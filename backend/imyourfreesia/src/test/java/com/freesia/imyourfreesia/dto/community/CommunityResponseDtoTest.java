package com.freesia.imyourfreesia.dto.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityResponseDtoTest {
    private Community community;
    private CommunityFile communityFile;

    public static CommunityResponseDto testCommunityResponseDto(Community community, CommunityFile communityFile) {
        return new CommunityResponseDto(community, Collections.singletonList(communityFile.getId()));
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(community);
    }

    @Test
    @DisplayName("CommunityResponseDto 생성 테스트")
    void testCommunityResponseDtoSave() {
        CommunityResponseDto communityResponseDto = testCommunityResponseDto(community, communityFile);

        assertEquals(community.getId(), communityResponseDto.getCommunityId());
        assertEquals(community.getUser().getId(), communityResponseDto.getUserId());
        assertEquals(community.getUser().getEmail(), communityResponseDto.getEmail());
        assertEquals(community.getUser().getNickName(), communityResponseDto.getNickName());
        assertEquals(community.getTitle(), communityResponseDto.getTitle());
        assertEquals(community.getContent(), communityResponseDto.getContent());
        assertEquals(community.getCategory().getCategoryName(), communityResponseDto.getCategory());
        assertEquals(community.getFiles().get(0).getId(), communityResponseDto.getFileIdList().get(0));
        assertEquals(community.getCreatedDate(), communityResponseDto.getCreatedDate());
        assertEquals(community.getModifiedDate(), communityResponseDto.getModifiedDate());
    }
}