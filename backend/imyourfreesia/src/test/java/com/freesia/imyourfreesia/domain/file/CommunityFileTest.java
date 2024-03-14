package com.freesia.imyourfreesia.domain.file;

import static com.freesia.imyourfreesia.domain.file.ChallengeFileTest.FILE_ID;
import static com.freesia.imyourfreesia.domain.file.ChallengeFileTest.FILE_ORIGINAL_NAME;
import static com.freesia.imyourfreesia.domain.file.ChallengeFileTest.FILE_PATH;
import static com.freesia.imyourfreesia.domain.file.ChallengeFileTest.FILE_SIZE;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.community.Community;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityFileTest {
    private CommunityFile communityFile;

    public static CommunityFile testCommunityFile() {
        return CommunityFile.builder()
                .id(FILE_ID)
                .origFileName(FILE_ORIGINAL_NAME)
                .filePath(FILE_PATH)
                .fileSize(FILE_SIZE)
                .build();
    }

    @BeforeEach
    void setUp() {
        communityFile = testCommunityFile();
    }

    @Test
    @DisplayName("커뮤니티 파일 추가 테스트")
    void testCommunityFileSave() {
        assertNotNull(communityFile);
        assertEquals(FILE_ORIGINAL_NAME, communityFile.getOrigFileName());
        assertEquals(FILE_PATH, communityFile.getFilePath());
        assertEquals(FILE_SIZE, communityFile.getFileSize());
    }

    @Test
    @DisplayName("커뮤니티 파일의 커뮤니티 연관관계 설정 테스트")
    void testSetCommunity() {
        Community community = mock(Community.class);
        communityFile.setCommunity(community);

        assertEquals(community, communityFile.getCommunity());
    }
}