package com.freesia.imyourfreesia.dto.challenge;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChallengeUpdateRequestDtoTest {
    private final ValidatorUtil<ChallengeUpdateRequestDto> validatorUtil = new ValidatorUtil<>();
    private Challenge challenge;
    private ChallengeRequestVO challengeRequestVO;

    public static ChallengeUpdateRequestDto testChallengeUpdateRequestDto(ChallengeRequestVO challengeRequestVO) {
        return ChallengeUpdateRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        User user = UserTest.testUser();
        challengeRequestVO = ChallengeRequestVOTest.testChallengeRequestVO(challenge, user);
    }

    @Test
    @DisplayName("ChallengeUpdateRequestDto 생성 테스트")
    void testChallengeUpdateRequestDtoSave() {
        ChallengeUpdateRequestDto challengeUpdateRequestDto = testChallengeUpdateRequestDto(challengeRequestVO);

        assertEquals(challenge.getTitle(), challengeUpdateRequestDto.getTitle());
        assertEquals(challenge.getContent(), challengeUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        ChallengeUpdateRequestDto challengeUpdateRequestDto = new ChallengeUpdateRequestDto();

        assertNotNull(challengeUpdateRequestDto);
        assertNull(challengeUpdateRequestDto.getTitle());
        assertNull(challengeUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("챌린지 제목 유효성 검증 테스트")
    void title_validation() {
        challengeRequestVO.setTitle(INVALID_EMPTY);
        ChallengeUpdateRequestDto challengeUpdateRequestDto = ChallengeUpdateRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();

        validatorUtil.validate(challengeUpdateRequestDto);
    }

    @Test
    @DisplayName("챌린지 내용 유효성 검증 테스트")
    void content_validation() {
        challengeRequestVO.setContent(INVALID_EMPTY);
        ChallengeUpdateRequestDto challengeUpdateRequestDto = ChallengeUpdateRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();

        validatorUtil.validate(challengeUpdateRequestDto);
    }
}