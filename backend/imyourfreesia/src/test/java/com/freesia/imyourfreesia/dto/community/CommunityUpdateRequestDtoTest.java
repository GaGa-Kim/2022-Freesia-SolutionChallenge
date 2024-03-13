package com.freesia.imyourfreesia.dto.community;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityUpdateRequestDtoTest {
    private final ValidatorUtil<CommunityUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private Community community;
    private CommunityRequestVO communityRequestVO;

    public static CommunityUpdateRequestDto testCommunityUpdateRequestDto(CommunityRequestVO communityRequestVO) {
        return CommunityUpdateRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        User user = UserTest.testUser();
        communityRequestVO = CommunityRequestVOTest.testCommunityRequestVO(community, user);
    }

    @Test
    @DisplayName("CommunityUpdateRequestDto 생성 테스트")
    void testCommunityUpdateRequestDtoSave() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(communityRequestVO);

        assertEquals(community.getTitle(), communityUpdateRequestDto.getTitle());
        assertEquals(community.getContent(), communityUpdateRequestDto.getContent());
        assertEquals(community.getCategory().getCategoryName(), communityUpdateRequestDto.getCategory());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommunityUpdateRequestDto communityUpdateRequestDto = new CommunityUpdateRequestDto();

        assertNotNull(communityUpdateRequestDto);
        assertNull(communityUpdateRequestDto.getTitle());
        assertNull(communityUpdateRequestDto.getContent());
        assertNull(communityUpdateRequestDto.getCategory());
    }

    @Test
    @DisplayName("커뮤니티 제목 유효성 검증 테스트")
    void title_validation() {
        communityRequestVO.setTitle(INVALID_EMPTY);
        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 내용 유효성 검증 테스트")
    void content_validation() {
        communityRequestVO.setContent(INVALID_EMPTY);
        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 카테고리 유효성 검증 테스트")
    void category_validation() {
        communityRequestVO.setCategory(INVALID_EMPTY);
        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDto.builder()
                .communityRequestVO(communityRequestVO)
                .build();

        validatorUtil.validate(communityUpdateRequestDto);
    }
}