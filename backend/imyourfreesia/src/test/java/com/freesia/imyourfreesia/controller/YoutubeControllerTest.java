package com.freesia.imyourfreesia.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.youtube.Youtube;
import com.freesia.imyourfreesia.domain.youtube.YoutubeTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.youtube.YoutubeService;
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

@WebMvcTest(controllers = YoutubeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class YoutubeControllerTest {
    private Youtube youtube;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private YoutubeService youtubeService;

    @BeforeEach
    void setUp() {
        youtube = YoutubeTest.testYoutube();
    }

    @Test
    @DisplayName("유튜브 영상 전체 조회 테스트")
    void testList() throws Exception {
        when(youtubeService.findAll()).thenReturn(Collections.singletonList(youtube));

        mockMvc.perform(get("/youtube")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(youtubeService).findAll();
    }
}