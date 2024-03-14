package com.freesia.imyourfreesia.domain.center;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CenterTest {
    public static final Long CENTER_ID = 1L;
    public static final String CENTER_NAME = "강동새일센터";
    public static final String CENTER_ADDRESS = "서울특별시 강동구 양재대로 1458 (길동) 길동빌딩 5층, 6층 (길동)";
    public static final String NEW_CENTER_ADDRESS = "서울특별시 강동구 양재대로 1458 (길동) 길동빌딩 6층, 7층 (길동)";
    public static final String CENTER_CONTACT = "02-475-0108";
    public static final String CENTER_WEB_SITE_URL = "https://gd.seoulwomanup.or.kr";
    private Center center;

    public static Center testCenter() {
        return Center.builder()
                .id(CENTER_ID)
                .name(CENTER_NAME)
                .address(CENTER_ADDRESS)
                .contact(CENTER_CONTACT)
                .websiteUrl(CENTER_WEB_SITE_URL)
                .build();
    }

    @BeforeEach
    void setUp() {
        center = testCenter();
    }

    @Test
    @DisplayName("센터 추가 테스트")
    void testCenterSave() {
        assertNotNull(center);
        assertEquals(CENTER_ID, center.getId());
        assertEquals(CENTER_NAME, center.getName());
        assertEquals(CENTER_ADDRESS, center.getAddress());
        assertEquals(CENTER_CONTACT, center.getContact());
        assertEquals(CENTER_WEB_SITE_URL, center.getWebsiteUrl());
    }

    @Test
    @DisplayName("센터 주소 수정 테스트")
    void testUpdate() {
        center.update(NEW_CENTER_ADDRESS);

        assertEquals(NEW_CENTER_ADDRESS, center.getAddress());
    }
}