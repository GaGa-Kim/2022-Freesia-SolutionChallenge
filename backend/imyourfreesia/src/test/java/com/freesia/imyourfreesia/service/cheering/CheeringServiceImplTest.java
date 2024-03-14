package com.freesia.imyourfreesia.service.cheering;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.domain.cheering.CheeringRepository;
import com.freesia.imyourfreesia.domain.cheering.CheeringTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDtoTest;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CheeringServiceImplTest {
    private Cheering cheering;
    private User user;

    @Mock
    private CheeringRepository cheeringRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private CheeringServiceImpl cheeringService;

    @BeforeEach
    void setUp() {
        cheering = CheeringTest.testCheering();
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("응원 저장 테스트")
    void testSaveCheering() {
        when(cheeringRepository.save(any(Cheering.class))).thenReturn(cheering);

        CheeringSaveRequestDto cheeringSaveRequestDto = CheeringSaveRequestDtoTest.testCheeringSaveRequestDto(cheering);
        Cheering result = cheeringService.saveCheering(cheeringSaveRequestDto);

        assertNotNull(result);
        assertEquals(cheering.getRecipientEmail(), result.getRecipientEmail());
        assertEquals(cheering.getSenderEmail(), result.getSenderEmail());

        verify(cheeringRepository).save(any(Cheering.class));
    }

    @Test
    @DisplayName("응원 삭제 테스트")
    void testDeleteCheering() {
        doNothing().when(cheeringRepository).deleteById(anyLong());

        cheeringService.deleteCheering(cheering.getId());

        verify(cheeringRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("이메일에 따른 응원 개수 조회 테스트")
    void testCountByRecipientEmail() {
        when(cheeringRepository.countByRecipientEmail(anyString())).thenReturn(1L);

        Long result = cheeringService.countByRecipientEmail(cheering.getRecipientEmail());

        assertEquals(1, result);

        verify(cheeringRepository).countByRecipientEmail(anyString());
    }

    @Test
    @DisplayName("응원 랭킹 조회 테스트")
    void testCheeringRankList() {
        when(userService.findUserList()).thenReturn(Collections.singletonList(user));

        List<Map<String, Object>> result = cheeringService.cheeringRankList();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("이메일에 따른 일주일 응원 개수 조회 테스트")
    void testCountByCreatedDateBetweenAndRecipientEmail() {
        when(cheeringRepository.countByCreatedDateBetweenAndRecipientEmail(any(), any(), anyString())).thenReturn(1L);

        Long result = cheeringService.countByCreatedDateBetweenAndRecipientEmail(cheering.getRecipientEmail());

        assertEquals(1, result);

        verify(cheeringRepository).countByCreatedDateBetweenAndRecipientEmail(any(), any(), anyString());
    }

    @Test
    @DisplayName("상대방 이메일과 내 이메일을 가지고 상대방 응원 여부 조회 테스트")
    void testExitsBySenderEmailAndRecipientEmail() {
        when(cheeringRepository.existsBySenderEmailAndRecipientEmail(anyString(), anyString())).thenReturn(true);

        boolean result = cheeringService.exitsBySenderEmailAndRecipientEmail(cheering.getSenderEmail(), cheering.getRecipientEmail());

        assertTrue(result);

        verify(cheeringRepository).existsBySenderEmailAndRecipientEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("이메일에 따른 응원한 회원 목록 조회 테스트")
    void testFindCheeringListByUser() {
        when(cheeringRepository.findBySenderEmail(anyString())).thenReturn(Collections.singletonList(cheering));

        List<Cheering> result = cheeringService.findCheeringListByUser(cheering.getSenderEmail());

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(cheeringRepository).findBySenderEmail(anyString());
    }
}