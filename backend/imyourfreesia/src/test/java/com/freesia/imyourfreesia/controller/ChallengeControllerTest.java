package com.freesia.imyourfreesia.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDtoTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVO;
import com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVOTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.challenge.ChallengeService;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@WebMvcTest(controllers = ChallengeController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class ChallengeControllerTest {
    private Challenge challenge;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ChallengeService challengeService;

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(UserTest.testUser());
        ChallengeFile challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(challenge);
    }

    @Test
    @DisplayName("챌린지 저장 테스트")
    void testSave() throws Exception {
        ChallengeResponseDto challengeResponseDto = ChallengeResponseDtoTest.testChallengeResponseDto(challenge);
        when(challengeService.saveChallenge(any(), any())).thenReturn(challengeResponseDto);

        ChallengeRequestVO challengeRequestVO = ChallengeRequestVOTest.testChallengeRequestVO(challenge, challenge.getUser());
        mockMvc.perform(multipart("/api/challenges")
                        .file((MockMultipartFile) challengeRequestVO.getFiles().get(0))
                        .param("userId", String.valueOf(challengeRequestVO.getUserId()))
                        .param("title", challengeRequestVO.getTitle())
                        .param("content", challengeRequestVO.getContent())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).saveChallenge(any(), any());
    }

    @Test
    @DisplayName("챌린지 리스트 조회 테스트")
    void testList() throws Exception {
        List<ChallengeListResponseDto> challengeListResponseDtoList = Collections.singletonList(ChallengeListResponseDtoTest.testChallengeListResponseDto(challenge));
        when(challengeService.getAllChallengeList()).thenReturn(challengeListResponseDtoList);

        mockMvc.perform(get("/challenges")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).getAllChallengeList();
    }

    @Test
    @DisplayName("챌린지 상세 조회 테스트")
    void testView() throws Exception {
        ChallengeResponseDto challengeResponseDto = ChallengeResponseDtoTest.testChallengeResponseDto(challenge);
        when(challengeService.getChallengeById(any())).thenReturn(challengeResponseDto);

        mockMvc.perform(get("/challenges/{challengeId}", challenge.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).getChallengeById(any());
    }

    @Test
    @DisplayName("챌린지 수정 테스트")
    void testUpdate() throws Exception {
        ChallengeResponseDto challengeResponseDto = ChallengeResponseDtoTest.testChallengeResponseDto(challenge);
        when(challengeService.updateChallenge(anyLong(), any(), any())).thenReturn(challengeResponseDto);

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/challenges/{challengeId}", challenge.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        ChallengeRequestVO challengeRequestVO = ChallengeRequestVOTest.testChallengeRequestVO(challenge, challenge.getUser());
        mockMvc.perform(builder
                        .file((MockMultipartFile) challengeRequestVO.getFiles().get(0))
                        .param("title", challengeRequestVO.getTitle())
                        .param("content", challengeRequestVO.getContent())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).updateChallenge(anyLong(), any(), any());
    }

    @Test
    @DisplayName("챌린지 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(challengeService).deleteChallenge(anyLong());

        mockMvc.perform(delete("/api/challenges/{challengeId}", challenge.getId())
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(challengeService).deleteChallenge(anyLong());
    }

    @Test
    @DisplayName("챌린지 파일 ByteArray 조회")
    void testFileByteArray() throws Exception {
        byte[] fileByteArray = challenge.getFiles().get(0).getFilePath().getBytes();
        when(challengeService.getFileByteArray(anyLong())).thenReturn(Base64.getEncoder().encodeToString(fileByteArray));

        mockMvc.perform(get("/challenges/file/{fileId}", challenge.getFiles().get(0).getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(challengeService).getFileByteArray(anyLong());
    }
}