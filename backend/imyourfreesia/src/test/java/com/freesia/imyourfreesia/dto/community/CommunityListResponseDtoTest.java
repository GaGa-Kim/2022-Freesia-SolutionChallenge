package com.freesia.imyourfreesia.dto.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityListResponseDtoTest {
    private Community community;

    public static CommunityListResponseDto testCommunityListResponseDto(Community community) {
        return new CommunityListResponseDto(community);
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        CommunityFile communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(community);
    }

    @Test
    @DisplayName("CommunityListResponseDto 생성 테스트")
    void testCommunityListResponseDtoSave() {
        CommunityListResponseDto communityListResponseDto = testCommunityListResponseDto(community);

        assertEquals(community.getId(), communityListResponseDto.getCommunityId());
        assertEquals(community.getUser().getId(), communityListResponseDto.getUserId());
        assertEquals(community.getUser().getEmail(), communityListResponseDto.getEmail());
        assertEquals(community.getUser().getNickName(), communityListResponseDto.getNickName());
        assertEquals(community.getTitle(), communityListResponseDto.getTitle());
        assertEquals(community.getContent(), communityListResponseDto.getContent());
        assertEquals(community.getCategory().getCategoryName(), communityListResponseDto.getCategory());
        assertEquals(community.getFiles().get(0).getId(), communityListResponseDto.getThumbnailFileId());
        assertEquals(community.getCreatedDate(), communityListResponseDto.getCreatedDate());
        assertEquals(community.getModifiedDate(), communityListResponseDto.getModifiedDate());
    }
}