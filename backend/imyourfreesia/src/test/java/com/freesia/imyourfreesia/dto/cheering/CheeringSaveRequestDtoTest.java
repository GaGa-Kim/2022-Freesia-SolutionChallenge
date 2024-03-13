package com.freesia.imyourfreesia.dto.cheering;

import static com.freesia.imyourfreesia.dto.auth.OAuth2UserInfoRequestDtoTest.INVALID_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.domain.cheering.CheeringTest;
import com.freesia.imyourfreesia.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheeringSaveRequestDtoTest {
    private final ValidatorUtil<CheeringSaveRequestDto> validatorUtil = new ValidatorUtil<>();
    private Cheering cheering;

    public static CheeringSaveRequestDto testCheeringSaveRequestDto(Cheering cheering) {
        return CheeringSaveRequestDto.builder()
                .recipientEmail(cheering.getRecipientEmail())
                .senderEmail(cheering.getSenderEmail())
                .build();
    }

    @BeforeEach
    void setUp() {
        cheering = CheeringTest.testCheering();
    }

    @Test
    @DisplayName("CheeringSaveRequestDto 생성 테스트")
    void testCheeringSaveRequestDtoSave() {
        CheeringSaveRequestDto cheeringSaveRequestDto = testCheeringSaveRequestDto(cheering);

        assertEquals(cheering.getRecipientEmail(), cheeringSaveRequestDto.getRecipientEmail());
        assertEquals(cheering.getSenderEmail(), cheeringSaveRequestDto.getSenderEmail());

    }

    @Test
    @DisplayName("CheeringSaveRequestDto toEntity 생성 테스트")
    void testCheeringSaveRequestDtoEntity() {
        CheeringSaveRequestDto cheeringSaveRequestDto = testCheeringSaveRequestDto(cheering);

        Cheering cheeringEntity = cheeringSaveRequestDto.toEntity();

        assertNotNull(cheeringEntity);
        assertEquals(cheeringSaveRequestDto.getRecipientEmail(), cheeringEntity.getRecipientEmail());
        assertEquals(cheeringSaveRequestDto.getSenderEmail(), cheeringEntity.getSenderEmail());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CheeringSaveRequestDto cheeringSaveRequestDto = new CheeringSaveRequestDto();

        assertNotNull(cheeringSaveRequestDto);
        assertNull(cheeringSaveRequestDto.getRecipientEmail());
        assertNull(cheeringSaveRequestDto.getSenderEmail());
    }

    @Test
    @DisplayName("응원 받는 회원 이메일 유효성 검증 테스트")
    void recipientEmail_validation() {
        CheeringSaveRequestDto cheeringSaveRequestDto = CheeringSaveRequestDto.builder()
                .recipientEmail(INVALID_EMAIL)
                .senderEmail(cheering.getSenderEmail())
                .build();

        validatorUtil.validate(cheeringSaveRequestDto);
    }

    @Test
    @DisplayName("응원 하는 회원 이메일 유효성 검증 테스트")
    void senderEmail_validation() {
        CheeringSaveRequestDto cheeringSaveRequestDto = CheeringSaveRequestDto.builder()
                .recipientEmail(cheering.getRecipientEmail())
                .senderEmail(INVALID_EMAIL)
                .build();

        validatorUtil.validate(cheeringSaveRequestDto);
    }
}