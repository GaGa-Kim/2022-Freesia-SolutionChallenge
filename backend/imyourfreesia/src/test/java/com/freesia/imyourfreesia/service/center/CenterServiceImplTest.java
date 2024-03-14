package com.freesia.imyourfreesia.service.center;

import static com.freesia.imyourfreesia.domain.center.CenterRepositoryTest.ADDRESS_KEYWORD;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.center.Center;
import com.freesia.imyourfreesia.domain.center.CenterRepository;
import com.freesia.imyourfreesia.domain.center.CenterTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CenterServiceImplTest {
    private Center center;
    private List<Center> centerList;

    @Mock
    private CenterRepository centerRepository;
    @Mock
    private CenterCrawler centerCrawler;
    @InjectMocks
    private CenterServiceImpl centerService;

    @BeforeEach
    void setUp() {
        center = CenterTest.testCenter();
        centerList = Collections.singletonList(center);
    }

    @Test
    @DisplayName("센터 정보 추출 후 저장 테스트")
    void testSaveCenterList() {
        when(centerCrawler.crawlSaeilCenters()).thenReturn(centerList);
        when(centerCrawler.crawlSaeilCenters()).thenReturn(centerList);
        when(centerRepository.saveAll(anyList())).thenReturn(centerList);

        centerService.saveCenterList();

        verify(centerCrawler).crawlSaeilCenters();
        verify(centerCrawler).crawlWomanUpCenters();
        verify(centerRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("지역에 따른 센터 목록 조회 테스트")
    void testFindCenterListByAddress() {
        when(centerRepository.findByAddressContains(anyString())).thenReturn(centerList);

        List<Center> result = centerService.findCenterListByAddress(ADDRESS_KEYWORD);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(center.getAddress(), result.get(0).getAddress());
        assertTrue(result.get(0).getAddress().contains(ADDRESS_KEYWORD));

        verify(centerRepository).findByAddressContains(anyString());
    }

    @Test
    @DisplayName("전체 센터 목록 조회 테스트")
    void testFindCenterList() {
        when(centerRepository.findAll()).thenReturn(centerList);

        List<Center> result = centerService.findCenterList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(center.getAddress(), result.get(0).getAddress());

        verify(centerRepository).findAll();
    }
}