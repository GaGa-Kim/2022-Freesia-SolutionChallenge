package com.freesia.imyourfreesia.domain.cheering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CheeringRepositoryTest {
    private final LocalDate startDatetime = LocalDate.now();
    private final LocalDate endDatetime = LocalDate.now().plusDays(7);
    private Cheering cheering;

    @Autowired
    private CheeringRepository cheeringRepository;

    @BeforeEach
    void setUp() {
        cheering = CheeringTest.testCheering();
        cheering = cheeringRepository.save(cheering);
    }

    @AfterEach
    void clean() {
        cheeringRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일에 따른 응원 개수 조회 테스트")
    void testCountByRecipientEmail() {
        Long countCheering = cheeringRepository.countByRecipientEmail(cheering.getRecipientEmail());

        assertEquals(1, countCheering);
    }

    @Test
    @DisplayName("응워 누른 회원의 이메일에 따른 응원 목록 조회 테스트")
    void testFindBySenderEmail() {
        List<Cheering> foundCheeringList = cheeringRepository.findBySenderEmail(cheering.getSenderEmail());

        assertNotNull(foundCheeringList);
        assertEquals(1, foundCheeringList.size());
        assertEquals(cheering.getSenderEmail(), foundCheeringList.get(0).getSenderEmail());
    }

    @Test
    @DisplayName("응원 누른 회원과 응원 받은 회원 이메일에 따라 응원 존재 유무 조회 테스트")
    void testExistsBySenderEmailAndRecipientEmail() {
        boolean foundCheering = cheeringRepository.existsBySenderEmailAndRecipientEmail(cheering.getSenderEmail(), cheering.getRecipientEmail());

        assertTrue(foundCheering);
    }

    @Test
    @DisplayName("이메일에 따른 일주일 간의 응원 개수 조회 테스트")
    void testCountByCreatedDateBetweenAndRecipientEmail() {
        Long countCheering = cheeringRepository.countByCreatedDateBetweenAndRecipientEmail(startDatetime, endDatetime, cheering.getRecipientEmail());

        assertEquals(1, countCheering);
    }
}