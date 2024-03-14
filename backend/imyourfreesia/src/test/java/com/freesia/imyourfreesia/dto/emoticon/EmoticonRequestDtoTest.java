package com.freesia.imyourfreesia.dto.emoticon;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EmoticonRequestDtoTest {
    private final ValidatorUtil<EmoticonRequestDto> validatorUtil = new ValidatorUtil<>();
    private Emoticon emoticon;

    public static EmoticonRequestDto testEmoticonRequestDto(Emoticon emoticon) {
        return EmoticonRequestDto.builder()
                .email(emoticon.getUser().getEmail())
                .challengeId(emoticon.getChallenge().getId())
                .emoticonName(emoticon.getName().getEmoticonName())
                .build();
    }

    @BeforeEach
    void setUp() {
        emoticon = EmoticonTest.testEmoticon();
        emoticon.setUser(UserTest.testUser());
        emoticon.setChallenge(ChallengeTest.testChallenge());
    }

    @Test
    @DisplayName("EmoticonRequestDto 생성 테스트")
    void testEmoticonRequestDtoSave() {
        EmoticonRequestDto emoticonRequestDto = testEmoticonRequestDto(emoticon);

        assertEquals(emoticon.getUser().getEmail(), emoticonRequestDto.getEmail());
        assertEquals(emoticon.getChallenge().getId(), emoticonRequestDto.getChallengeId());
        assertEquals(emoticon.getName().getEmoticonName(), emoticonRequestDto.getEmoticonName());
    }

    @Test
    @DisplayName("EmoticonRequestDto toEntity 생성 테스트")
    void testEmoticonRequestDtoEntity() {
        EmoticonRequestDto emoticonRequestDto = testEmoticonRequestDto(emoticon);

        Emoticon emoticonEntity = emoticonRequestDto.toEntity();
        emoticonEntity.setUser(emoticon.getUser());
        emoticonEntity.setChallenge(emoticon.getChallenge());

        assertNotNull(emoticonEntity);
        assertEquals(emoticonRequestDto.getEmail(), emoticonEntity.getUser().getEmail());
        assertEquals(emoticonRequestDto.getChallengeId(), emoticonEntity.getChallenge().getId());
        assertEquals(emoticonRequestDto.getEmoticonName(), emoticonEntity.getName().getEmoticonName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        EmoticonRequestDto emoticonRequestDto = new EmoticonRequestDto();

        assertNotNull(emoticonRequestDto);
        assertNull(emoticonRequestDto.getEmail());
        assertNull(emoticonRequestDto.getChallengeId());
        assertNull(emoticonRequestDto.getEmoticonName());
    }

    @Test
    @DisplayName("이모티콘 작성 회원 이메일 유효성 검증 테스트")
    void email_validation() {
        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDto.builder()
                .email(INVALID_EMAIL)
                .challengeId(emoticon.getChallenge().getId())
                .emoticonName(emoticon.getName().getEmoticonName())
                .build();

        validatorUtil.validate(emoticonRequestDto);
    }

    @Test
    @DisplayName("이모티콘의 챌린지 아이디 유효성 검증 테스트")
    void challengeId_validation() {
        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDto.builder()
                .email(emoticon.getUser().getEmail())
                .challengeId(null)
                .emoticonName(emoticon.getName().getEmoticonName())
                .build();

        validatorUtil.validate(emoticonRequestDto);
    }

    @Test
    @DisplayName("이모티콘 이름 유효성 검증 테스트")
    void emoticonName_validation() {
        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDto.builder()
                .email(emoticon.getUser().getEmail())
                .challengeId(emoticon.getChallenge().getId())
                .emoticonName(INVALID_EMPTY)
                .build();

        validatorUtil.validate(emoticonRequestDto);
    }
}