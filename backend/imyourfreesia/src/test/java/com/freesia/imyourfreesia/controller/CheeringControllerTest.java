package com.freesia.imyourfreesia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.cheering.Cheering;
import com.freesia.imyourfreesia.domain.cheering.CheeringTest;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDto;
import com.freesia.imyourfreesia.dto.cheering.CheeringSaveRequestDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.cheering.CheeringService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = CheeringController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CheeringControllerTest {
    private Cheering cheering;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheeringService cheeringService;

    @BeforeEach
    void setUp() {
        cheering = CheeringTest.testCheering();
    }

    @Test
    @DisplayName("응원 저장 테스트")
    void testSave() throws Exception {
        when(cheeringService.saveCheering(any())).thenReturn(cheering);

        CheeringSaveRequestDto cheeringSaveRequestDto = CheeringSaveRequestDtoTest.testCheeringSaveRequestDto(cheering);
        mockMvc.perform(post("/api/cheerings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(cheeringSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).saveCheering(any());
    }

    @Test
    @DisplayName("응원 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(cheeringService).deleteCheering(anyLong());

        mockMvc.perform(delete("/api/cheerings")
                        .param("cheeringId", String.valueOf(cheering.getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(cheeringService).deleteCheering(anyLong());
    }

    @Test
    @DisplayName("응원 전체 개수 조회 테스트")
    void testCountAll() throws Exception {
        when(cheeringService.countByRecipientEmail(anyString())).thenReturn(1L);

        mockMvc.perform(get("/cheerings/count")
                        .param("userEmail", cheering.getRecipientEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).countByRecipientEmail(anyString());
    }

    @Test
    @DisplayName("응원 일주일 개수 조회 테스트")
    void testCountWeek() throws Exception {
        when(cheeringService.countByCreatedDateBetweenAndRecipientEmail(anyString())).thenReturn(1L);

        mockMvc.perform(get("/cheerings/count/week")
                        .param("userEmail", cheering.getRecipientEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).countByCreatedDateBetweenAndRecipientEmail(anyString());
    }

    @Test
    @DisplayName("응원 랭킹 Top 10 조회 테스트")
    void testRank() throws Exception {
        List<Map<String, Object>> rankingData = List.of(Collections.singletonMap(cheering.getRecipientEmail(), 1L));
        when(cheeringService.cheeringRankList()).thenReturn(rankingData);

        mockMvc.perform(get("/cheerings/ranking")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).cheeringRankList();
    }

    @Test
    @DisplayName("응원한 회원 목록 조회 테스트")
    void testMyList() throws Exception {
        when(cheeringService.findCheeringListByUser(anyString())).thenReturn(Collections.singletonList(cheering));

        mockMvc.perform(get("/api/cheerings/myList")
                        .param("userEmail", cheering.getRecipientEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).findCheeringListByUser(anyString());
    }

    @Test
    @DisplayName("상대방 응원 여부 조회 테스트")
    void testExistsCheering() throws Exception {
        when(cheeringService.exitsBySenderEmailAndRecipientEmail(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(get("/api/cheerings/exists")
                        .param("myEmail", cheering.getSenderEmail())
                        .param("yourEmail", cheering.getRecipientEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(cheeringService).exitsBySenderEmailAndRecipientEmail(anyString(), anyString());
    }
}