package com.freesia.imyourfreesia.domain.center;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CenterRepositoryTest {
    private static final String ADDRESS_KEYWORD = "강동구";

    private Center center;

    @Autowired
    private CenterRepository centerRepository;

    @BeforeEach
    void setUp() {
        center = CenterTest.testCenter();
    }

    @AfterEach
    void clean() {
        centerRepository.deleteAll();
    }

    @Test
    @DisplayName("주소에 따른 센터 목록 조회 테스트")
    void testFindByAddressContains() {
        List<Center> foundCenterList = centerRepository.findByAddressContains(ADDRESS_KEYWORD);

        assertNotNull(foundCenterList);
        assertEquals(2, foundCenterList.size());
        assertEquals(center.getAddress(), foundCenterList.get(0).getAddress());
    }
}