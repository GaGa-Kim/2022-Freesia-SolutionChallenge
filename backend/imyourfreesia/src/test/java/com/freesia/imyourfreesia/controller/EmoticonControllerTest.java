package com.freesia.imyourfreesia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.emoticon.Emoticon;
import com.freesia.imyourfreesia.domain.emoticon.EmoticonTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonCountResponseDtoTest;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDto;
import com.freesia.imyourfreesia.dto.emoticon.EmoticonRequestDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.emoticon.EmoticonService;
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

@WebMvcTest(controllers = EmoticonController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class EmoticonControllerTest {
    private Emoticon emoticon;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmoticonService emoticonService;

    @BeforeEach
    void setUp() {
        emoticon = EmoticonTest.testEmoticon();
        emoticon.setUser(UserTest.testUser());
        emoticon.setChallenge(ChallengeTest.testChallenge());
    }

    @Test
    @DisplayName("이모티콘 저장 테스트")
    void testSave() throws Exception {
        EmoticonCountResponseDto emoticonCountResponseDto = EmoticonCountResponseDtoTest.testEmoticonCountResponseDto();
        when(emoticonService.saveEmotion(any())).thenReturn(emoticonCountResponseDto);

        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDtoTest.testEmoticonRequestDto(emoticon);
        mockMvc.perform(post("/api/emoticons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emoticonRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(emoticonService).saveEmotion(any());
    }

    @Test
    @DisplayName("이모티콘 삭제 테스트")
    void testDelete() throws Exception {
        EmoticonCountResponseDto emoticonCountResponseDto = EmoticonCountResponseDtoTest.testEmoticonCountResponseDto();
        when(emoticonService.deleteEmotion(any())).thenReturn(emoticonCountResponseDto);

        EmoticonRequestDto emoticonRequestDto = EmoticonRequestDtoTest.testEmoticonRequestDto(emoticon);
        mockMvc.perform(delete("/api/emoticons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(emoticonRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(emoticonService).deleteEmotion(any());
    }

    @Test
    @DisplayName("이모티콘 조회 테스트")
    void testView() throws Exception {
        EmoticonCountResponseDto emoticonCountResponseDto = EmoticonCountResponseDtoTest.testEmoticonCountResponseDto();
        when(emoticonService.getEmoticonByChallengeAndUser(anyLong(), anyString())).thenReturn(emoticonCountResponseDto);

        mockMvc.perform(get("/api/emoticons/my")
                        .param("challengeId", String.valueOf(emoticon.getChallenge().getId()))
                        .param("email", emoticon.getUser().getEmail())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(emoticonService).getEmoticonByChallengeAndUser(anyLong(), anyString());
    }

    @Test
    @DisplayName("챌린지에 따른 이모티콘 개수 조회 테스트")
    void testCount() throws Exception {
        EmoticonCountResponseDto emoticonCountResponseDto = EmoticonCountResponseDtoTest.testEmoticonCountResponseDto();
        when(emoticonService.countByChallenge(anyLong())).thenReturn(emoticonCountResponseDto);

        mockMvc.perform(get("/emoticons/count")
                        .param("challengeId", String.valueOf(emoticon.getChallenge().getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(emoticonService).countByChallenge(anyLong());
    }
}