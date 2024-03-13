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

public class ChallengeSaveRequestDtoTest {
    private final ValidatorUtil<ChallengeSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private Challenge challenge;
    private ChallengeRequestVO challengeRequestVO;

    public static ChallengeSaveRequestDto testChallengeSaveRequestDto(ChallengeRequestVO challengeRequestVO) {
        return ChallengeSaveRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();
    }

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        User user = UserTest.testUser();
        challenge.setUser(user);
        challengeRequestVO = ChallengeRequestVOTest.testChallengeRequestVO(challenge, user);
    }

    @Test
    @DisplayName("ChallengeSaveRequestDto 생성 테스트")
    void testChallengeSaveRequestDtoSave() {
        ChallengeSaveRequestDto challengeSaveRequestDto = testChallengeSaveRequestDto(challengeRequestVO);

        assertEquals(challenge.getUser().getId(), challengeSaveRequestDto.getUserId());
        assertEquals(challenge.getTitle(), challengeSaveRequestDto.getTitle());
        assertEquals(challenge.getContent(), challengeSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("ChallengeSaveRequestDto toEntity 생성 테스트")
    void testChallengeSaveRequestDtoEntity() {
        ChallengeSaveRequestDto challengeSaveRequestDto = testChallengeSaveRequestDto(challengeRequestVO);

        Challenge challengeEntity = challengeSaveRequestDto.toEntity();
        challengeEntity.setUser(challenge.getUser());

        assertNotNull(challengeEntity);
        assertEquals(challengeSaveRequestDto.getUserId(), challengeEntity.getUser().getId());
        assertEquals(challengeSaveRequestDto.getTitle(), challengeEntity.getTitle());
        assertEquals(challengeSaveRequestDto.getContent(), challengeEntity.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        ChallengeSaveRequestDto challengeSaveRequestDto = new ChallengeSaveRequestDto();

        assertNotNull(challengeSaveRequestDto);
        assertNull(challengeSaveRequestDto.getUserId());
        assertNull(challengeSaveRequestDto.getTitle());
        assertNull(challengeSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("챌린지 작성 회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        challengeRequestVO.setUserId(null);
        ChallengeSaveRequestDto challengeSaveRequestDto = ChallengeSaveRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }

    @Test
    @DisplayName("챌린지 제목 유효성 검증 테스트")
    void title_validation() {
        challengeRequestVO.setTitle(INVALID_EMPTY);
        ChallengeSaveRequestDto challengeSaveRequestDto = ChallengeSaveRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }

    @Test
    @DisplayName("챌린지 내용 유효성 검증 테스트")
    void content_validation() {
        challengeRequestVO.setContent(INVALID_EMPTY);
        ChallengeSaveRequestDto challengeSaveRequestDto = ChallengeSaveRequestDto.builder()
                .challengeRequestVO(challengeRequestVO)
                .build();

        validatorUtil.validate(challengeSaveRequestDto);
    }
}