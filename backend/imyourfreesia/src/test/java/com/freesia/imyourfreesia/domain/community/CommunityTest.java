package com.freesia.imyourfreesia.domain.community;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityTest {
    public static final Long COMMUNITY_ID = 1L;
    public static final String COMMUNITY_TITLE = "제목";
    public static final String NEW_COMMUNITY_TITLE = "새 제목";
    public static final String COMMUNITY_CONTENT = "내용";
    public static final String NEW_COMMUNITY_CONTENT = "새 내용";
    public static final String COMMUNITY_CATEGORY = Category.WORRIES.getCategoryName();
    public static final String NEW_COMMUNITY_CATEGORY = Category.REVIEW.getCategoryName();
    private Community community;

    public static Community testCommunity() {
        return Community.builder()
                .id(COMMUNITY_ID)
                .title(COMMUNITY_TITLE)
                .content(COMMUNITY_CONTENT)
                .category(COMMUNITY_CATEGORY)
                .build();
    }

    @BeforeEach
    void setUp() {
        community = testCommunity();
    }

    @Test
    @DisplayName("커뮤니티 추가 테스트")
    void testCommunitySave() {
        assertNotNull(community);
        assertEquals(COMMUNITY_ID, community.getId());
        assertEquals(COMMUNITY_TITLE, community.getTitle());
        assertEquals(COMMUNITY_CONTENT, community.getContent());
        assertEquals(COMMUNITY_CATEGORY, community.getCategory().getCategoryName());
    }

    @Test
    @DisplayName("커뮤니티의 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        community.setUser(user);

        assertEquals(user, community.getUser());
    }

    @Test
    @DisplayName("커뮤니티 수정 테스트")
    void testUpdate() {
        community.update(NEW_COMMUNITY_TITLE, NEW_COMMUNITY_CONTENT, NEW_COMMUNITY_CATEGORY);

        assertEquals(NEW_COMMUNITY_TITLE, community.getTitle());
        assertEquals(NEW_COMMUNITY_CONTENT, community.getContent());
        assertEquals(NEW_COMMUNITY_CATEGORY, community.getCategory().getCategoryName());
    }

    @Test
    @DisplayName("커뮤니티의 모든 파일 삭제 테스트")
    void removeAllFiles() {
        CommunityFile communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(community);

        assertEquals(1, community.getFiles().size());

        community.removeAllFiles();

        assertEquals(0, community.getFiles().size());
    }
}