package com.freesia.imyourfreesia.domain.cheering;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheeringTest {
    public static final Long CHEERING_ID = 1L;
    public static final String RECIPIENT_EMAIL = "freesia@gmail.com";
    public static final String SENDER_EMAIL = "reboot@gmail.com";
    private Cheering cheering;

    public static Cheering testCheering() {
        return Cheering.builder()
                .id(CHEERING_ID)
                .recipientEmail(RECIPIENT_EMAIL)
                .senderEmail(SENDER_EMAIL)
                .build();
    }

    @BeforeEach
    void setUp() {
        cheering = testCheering();
    }

    @Test
    @DisplayName("응원 추가 테스트")
    void testCheeringSave() {
        assertNotNull(cheering);
        assertEquals(CHEERING_ID, cheering.getId());
        assertEquals(RECIPIENT_EMAIL, cheering.getRecipientEmail());
        assertEquals(SENDER_EMAIL, cheering.getSenderEmail());
    }
}