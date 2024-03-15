package com.freesia.imyourfreesia.service.community;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.community.Category;
import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityRequestVOTest;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDtoTest;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDtoTest;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDtoTest;
import com.freesia.imyourfreesia.service.file.FileService;
import com.freesia.imyourfreesia.service.user.UserService;
import com.freesia.imyourfreesia.util.FileHandler;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class CommunityServiceImplTest {
    private Community community;

    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private FileService communityFileServiceImpl;
    @Mock
    private UserService userService;
    @Mock
    private FileHandler fileHandler;
    @InjectMocks
    private CommunityServiceImpl communityService;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        CommunityFile communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(community);
    }

    @Test
    @DisplayName("커뮤니티 저장 테스트")
    void testSaveCommunity() throws Exception {
        when(userService.findUserByEmail(anyString())).thenReturn(community.getUser());
        when(fileHandler.saveFiles(anyList())).thenReturn(Collections.singletonList(FileSaveRequestDtoTest.testCommunityFileSaveRequestDto(community.getFiles().get(0))));
        doNothing().when(communityFileServiceImpl).saveFile(any(CommunityFile.class));
        when(communityRepository.save(any(Community.class))).thenReturn(community);

        CommunitySaveRequestDto communitySaveRequestDto = CommunitySaveRequestDtoTest.testCommunitySaveRequestDto(
                CommunityRequestVOTest.testCommunityRequestVO(community, community.getUser()));
        CommunityResponseDto result = communityService.saveCommunity(communitySaveRequestDto, Collections.singletonList(mock(MultipartFile.class)));

        assertNotNull(result);
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickName(), result.getNickName());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getCategory().getCategoryName(), result.getCategory());
        assertEquals(community.getCreatedDate(), result.getCreatedDate());
        assertEquals(community.getModifiedDate(), result.getModifiedDate());

        verify(userService).findUserByEmail(anyString());
        verify(communityRepository).save(any(Community.class));
    }

    @Test
    @DisplayName("카테고리에 따른 커뮤니티 목록 조회 테스트")
    void testGetCommunityListByCategory() {
        when(communityRepository.findByCategory(any(Category.class))).thenReturn(Collections.singletonList(community));

        List<CommunityListResponseDto> result = communityService.getCommunityListByCategory(community.getCategory().getCategoryName());

        assertNotNull(result);
        assertEquals(community.getUser().getId(), result.get(0).getUserId());
        assertEquals(community.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(community.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(community.getTitle(), result.get(0).getTitle());
        assertEquals(community.getContent(), result.get(0).getContent());
        assertEquals(community.getCategory().getCategoryName(), result.get(0).getCategory());
        assertEquals(community.getFiles().get(0).getId(), result.get(0).getThumbnailFileId());
        assertEquals(community.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(community.getModifiedDate(), result.get(0).getModifiedDate());

        verify(communityRepository).findByCategory(any(Category.class));
    }

    @Test
    @DisplayName("커뮤니티 아이디에 따른 커뮤니티 조회 테스트")
    void testFindCommunityById() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));

        Community result = communityService.findCommunityById(community.getId());

        assertNotNull(result);
        assertEquals(community.getUser(), result.getUser());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getCategory(), result.getCategory());
        assertEquals(community.getFiles(), result.getFiles());
        assertEquals(community.getCreatedDate(), result.getCreatedDate());
        assertEquals(community.getModifiedDate(), result.getModifiedDate());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 아이디에 따른 커뮤니티 상세 조회 테스트")
    void testGetCommunityById() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));

        CommunityResponseDto result = communityService.getCommunityById(community.getId());

        assertNotNull(result);
        assertEquals(community.getUser().getId(), result.getUserId());
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickName(), result.getNickName());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getCategory().getCategoryName(), result.getCategory());
        assertEquals(community.getFiles().get(0).getId(), result.getFileIdList().get(0));
        assertEquals(community.getCreatedDate(), result.getCreatedDate());
        assertEquals(community.getModifiedDate(), result.getModifiedDate());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 수정 테스트")
    void testUpdateCommunity() throws Exception {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));
        when(fileHandler.saveFiles(anyList())).thenReturn(Collections.singletonList(FileSaveRequestDtoTest.testCommunityFileSaveRequestDto(community.getFiles().get(0))));
        doNothing().when(communityFileServiceImpl).saveFile(any(CommunityFile.class));

        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDtoTest.testCommunityUpdateRequestDto(
                CommunityRequestVOTest.testCommunityRequestVO(community, community.getUser()));
        CommunityResponseDto result = communityService.updateCommunity(community.getId(), communityUpdateRequestDto, Collections.singletonList(mock(MultipartFile.class)));

        assertNotNull(result);
        assertEquals(community.getUser().getId(), result.getUserId());
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickName(), result.getNickName());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getCategory().getCategoryName(), result.getCategory());
        assertEquals(community.getFiles().get(0).getId(), result.getFileIdList().get(0));
        assertEquals(community.getCreatedDate(), result.getCreatedDate());
        assertEquals(community.getModifiedDate(), result.getModifiedDate());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 삭제 테스트")
    void testDeleteCommunity() {
        doNothing().when(communityRepository).deleteById(anyLong());

        communityService.deleteCommunity(community.getId());

        verify(communityRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("회원이 작성한 커뮤니티 목록 조회 테스트")
    void testGetCommunityListByUser() {
        when(userService.findUserByEmail(anyString())).thenReturn(community.getUser());
        when(communityRepository.findByUser(any(User.class))).thenReturn(Collections.singletonList(community));

        List<CommunityListResponseDto> result = communityService.getCommunityListByUser(community.getUser().getEmail());

        assertNotNull(result);
        assertEquals(community.getUser().getId(), result.get(0).getUserId());
        assertEquals(community.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(community.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(community.getTitle(), result.get(0).getTitle());
        assertEquals(community.getContent(), result.get(0).getContent());
        assertEquals(community.getCategory().getCategoryName(), result.get(0).getCategory());
        assertEquals(community.getFiles().get(0).getId(), result.get(0).getThumbnailFileId());
        assertEquals(community.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(community.getModifiedDate(), result.get(0).getModifiedDate());

        verify(userService).findUserByEmail(anyString());
        verify(communityRepository).findByUser(any(User.class));
    }
}