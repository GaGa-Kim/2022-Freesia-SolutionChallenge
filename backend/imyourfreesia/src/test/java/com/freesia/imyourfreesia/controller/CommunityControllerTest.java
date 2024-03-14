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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.freesia.imyourfreesia.config.SecurityConfig;
import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDtoTest;
import com.freesia.imyourfreesia.dto.community.CommunityRequestVO;
import com.freesia.imyourfreesia.dto.community.CommunityRequestVOTest;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDtoTest;
import com.freesia.imyourfreesia.jwt.JwtRequestFilter;
import com.freesia.imyourfreesia.service.community.CommunityService;
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

@WebMvcTest(controllers = CommunityController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CommunityControllerTest {
    private Community community;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommunityService communityService;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        CommunityFile communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(community);
    }

    @Test
    @DisplayName("커뮤니티 저장 테스트")
    void testSave() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.saveCommunity(any(), any())).thenReturn(communityResponseDto);

        CommunityRequestVO communityRequestVO = CommunityRequestVOTest.testCommunityRequestVO(community, community.getUser());
        mockMvc.perform(multipart("/api/communities")
                        .file((MockMultipartFile) communityRequestVO.getFiles().get(0))
                        .param("email", String.valueOf(communityRequestVO.getEmail()))
                        .param("title", communityRequestVO.getTitle())
                        .param("content", communityRequestVO.getContent())
                        .param("category", communityRequestVO.getCategory())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).saveCommunity(any(), any());
    }

    @Test
    @DisplayName("카테고리에 따른 (고민, 후기, 모임) 커뮤니티 리스트 조회 테스트")
    void testListByCategory() throws Exception {
        List<CommunityListResponseDto> communityListResponseDtoList = Collections.singletonList(CommunityListResponseDtoTest.testCommunityListResponseDto(community));
        when(communityService.getCommunityListByCategory(anyString())).thenReturn(communityListResponseDtoList);

        mockMvc.perform(get("/communities")
                        .param("category", community.getCategory().getCategoryName())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getCommunityListByCategory(anyString());
    }

    @Test
    @DisplayName("커뮤니티 상세 조회 테스트")
    void testView() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.getCommunityById(any())).thenReturn(communityResponseDto);

        mockMvc.perform(get("/communities/{communityId}", community.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getCommunityById(any());
    }

    @Test
    @DisplayName("커뮤니티 수정 테스트")
    void testUpdate() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.updateCommunity(anyLong(), any(), any())).thenReturn(communityResponseDto);

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/communities/{communityId}", community.getId());
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        CommunityRequestVO communityRequestVO = CommunityRequestVOTest.testCommunityRequestVO(community, community.getUser());
        mockMvc.perform(builder
                        .file((MockMultipartFile) communityRequestVO.getFiles().get(0))
                        .param("title", communityRequestVO.getTitle())
                        .param("content", communityRequestVO.getContent())
                        .param("category", communityRequestVO.getCategory())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).updateCommunity(anyLong(), any(), any());
    }

    @Test
    @DisplayName("커뮤니티 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(communityService).deleteCommunity(anyLong());

        mockMvc.perform(delete("/api/communities/{communityId}", community.getId())
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(communityService).deleteCommunity(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 파일 ByteArray 조회")
    void testFileByteArray() throws Exception {
        byte[] fileByteArray = community.getFiles().get(0).getFilePath().getBytes();
        when(communityService.getFileByteArray(anyLong())).thenReturn(Base64.getEncoder().encodeToString(fileByteArray));

        mockMvc.perform(get("/communities/file/{fileId}", community.getFiles().get(0).getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getFileByteArray(anyLong());
    }
}