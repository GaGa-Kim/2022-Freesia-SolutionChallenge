package com.freesia.imyourfreesia.service.file;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFileTest;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommunityFileServiceImplTest {
    private CommunityFile communityFile;

    @Mock
    private CommunityFileRepository communityFileRepository;
    @Mock
    private CommunityRepository communityRepository;
    @InjectMocks
    private CommunityFileServiceImpl communityFileService;

    @BeforeEach
    void setUp() {
        communityFile = CommunityFileTest.testCommunityFile();
        communityFile.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("커뮤니티 파일 저장 테스트")
    void testSaveFile() {
        when(communityFileRepository.save(any(CommunityFile.class))).thenReturn(communityFile);

        communityFileService.saveFile(communityFile);

        verify(communityFileRepository).save(any(CommunityFile.class));
    }

    @Test
    @DisplayName("파일 아이디에 따른 커뮤니티 파일 조회 테스트")
    void testGetFileById() {
        when(communityFileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(communityFile));

        FileResponseDto result = communityFileService.getFileById(communityFile.getCommunity().getId());

        assertNotNull(result);
        assertEquals(communityFile.getOrigFileName(), result.getOrigFileName());
        assertEquals(communityFile.getFilePath(), result.getFilePath());
        assertEquals(communityFile.getFileSize(), result.getFileSize());

        verify(communityFileRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 아이디에 따른 커뮤니티 파일 목록 조회 테스트")
    void testFindFileList() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(communityFile.getCommunity()));

        List<CommunityFile> result = communityFileService.findFileList(communityFile.getCommunity().getId());

        assertNotNull(result);
        assertEquals(communityFile.getOrigFileName(), result.get(0).getOrigFileName());
        assertEquals(communityFile.getFilePath(), result.get(0).getFilePath());
        assertEquals(communityFile.getFileSize(), result.get(0).getFileSize());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 아이디에 따른 커뮤니티 파일 아이디 목록 조회 테스트")
    void testGetFileListByCommunity() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(communityFile.getCommunity()));

        List<FileIdResponseDto> result = communityFileService.getFileListByCommunityOrChallenge(communityFile.getCommunity().getId());

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 파일 삭제 테스트")
    void testDeleteFile() {
        doNothing().when(communityFileRepository).deleteById(anyLong());

        communityFileService.deleteFile(communityFile.getId());

        verify(communityFileRepository).deleteById(anyLong());
    }
}