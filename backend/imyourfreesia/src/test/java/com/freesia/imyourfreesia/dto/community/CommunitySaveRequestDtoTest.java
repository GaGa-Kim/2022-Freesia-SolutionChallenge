package com.freesia.imyourfreesia.dto.community;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunitySaveRequestDtoTest {
    private final ValidatorUtil<CommunitySaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private Community community;
    private CommunityRequestVO communityRequestVO;

    public static CommunitySaveRequestDto testCommunitySaveRequestDto(CommunityRequestVO communityRequestVO) {
        return CommunitySaveRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        communityRequestVO = CommunityRequestVOTest.testCommunityRequestVO(community, community.getUser());
    }

    @Test
    @DisplayName("CommunitySaveRequestDto 생성 테스트")
    void testCommunitySaveRequestDtoSave() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(communityRequestVO);

        assertEquals(community.getUser().getEmail(), communitySaveRequestDto.getEmail());
        assertEquals(community.getTitle(), communitySaveRequestDto.getTitle());
        assertEquals(community.getContent(), communitySaveRequestDto.getContent());
        assertEquals(community.getCategory().getCategoryName(), communitySaveRequestDto.getCategory());
    }

    @Test
    @DisplayName("CommunitySaveRequestDto toEntity 생성 테스트")
    void testCommunitySaveRequestDtoEntity() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(communityRequestVO);

        Community communityEntity = communitySaveRequestDto.toEntity();
        communityEntity.setUser(community.getUser());

        assertNotNull(communityEntity);
        assertEquals(communitySaveRequestDto.getEmail(), communityEntity.getUser().getEmail());
        assertEquals(communitySaveRequestDto.getTitle(), communityEntity.getTitle());
        assertEquals(communitySaveRequestDto.getContent(), communityEntity.getContent());
        assertEquals(communitySaveRequestDto.getCategory(), communityEntity.getCategory().getCategoryName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommunitySaveRequestDto communitySaveRequestDto = new CommunitySaveRequestDto();

        assertNotNull(communitySaveRequestDto);
        assertNull(communitySaveRequestDto.getEmail());
        assertNull(communitySaveRequestDto.getTitle());
        assertNull(communitySaveRequestDto.getContent());
        assertNull(communitySaveRequestDto.getCategory());
    }

    @Test
    @DisplayName("커뮤니티 작성 회원 이메일 유효성 검증 테스트")
    void email_validation() {
        communityRequestVO.setEmail(INVALID_EMAIL);
        CommunitySaveRequestDto challengeSaveRequestDto = CommunitySaveRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 제목 유효성 검증 테스트")
    void title_validation() {
        communityRequestVO.setTitle(INVALID_EMPTY);
        CommunitySaveRequestDto challengeSaveRequestDto = CommunitySaveRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 내용 유효성 검증 테스트")
    void content_validation() {
        communityRequestVO.setContent(INVALID_EMPTY);
        CommunitySaveRequestDto challengeSaveRequestDto = CommunitySaveRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 카테고리 유효성 검증 테스트")
    void category_validation() {
        communityRequestVO.setContent(INVALID_EMPTY);
        CommunitySaveRequestDto challengeSaveRequestDto = CommunitySaveRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }
}