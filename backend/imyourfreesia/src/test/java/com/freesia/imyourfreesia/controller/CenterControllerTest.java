package com.freesia.imyourfreesia.controller;

import static com.freesia.imyourfreesia.domain.center.CenterRepositoryTest.ADDRESS_KEYWORD;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.center.Center;
import com.freesia.imyourfreesia.domain.center.CenterTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.center.CenterService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CenterController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CenterControllerTest {
    private Center center;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CenterService centerService;

    @BeforeEach
    void setUp() {
        center = CenterTest.testCenter();
    }

    @Test
    @DisplayName("지역 센터 정보 검색")
    void testListByAddress() throws Exception {
        when(centerService.findCenterListByAddress(anyString())).thenReturn(Collections.singletonList(center));

        mockMvc.perform(get("/centers")
                        .param("address", ADDRESS_KEYWORD)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(centerService).findCenterListByAddress(anyString());
    }

    @Test
    @DisplayName("센터 정보 전체 조회")
    void testsList() throws Exception {
        when(centerService.findCenterList()).thenReturn(Collections.singletonList(center));

        mockMvc.perform(get("/centers/all")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(centerService).findCenterList();
    }
}