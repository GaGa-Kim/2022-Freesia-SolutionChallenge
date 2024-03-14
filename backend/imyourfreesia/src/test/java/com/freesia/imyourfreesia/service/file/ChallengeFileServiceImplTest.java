package com.freesia.imyourfreesia.service.file;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileRepository;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
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
class ChallengeFileServiceImplTest {
    private ChallengeFile challengeFile;

    @Mock
    private ChallengeFileRepository challengeFileRepository;
    @Mock
    private ChallengeRepository challengeRepository;
    @InjectMocks
    private ChallengeFileServiceImpl challengeFileService;

    @BeforeEach
    void setUp() {
        challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(ChallengeTest.testChallenge());
    }

    @Test
    @DisplayName("챌린지 파일 저장 테스트")
    void testSaveFile() {
        when(challengeFileRepository.save(any(ChallengeFile.class))).thenReturn(challengeFile);

        challengeFileService.saveFile(challengeFile);

        verify(challengeFileRepository).save(any(ChallengeFile.class));
    }

    @Test
    @DisplayName("파일 아이디에 따른 챌린지 파일 조회 테스트")
    void testGetFileById() {
        when(challengeFileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challengeFile));

        FileResponseDto result = challengeFileService.getFileById(challengeFile.getChallenge().getId());

        assertNotNull(result);
        assertEquals(challengeFile.getOrigFileName(), result.getOrigFileName());
        assertEquals(challengeFile.getFilePath(), result.getFilePath());
        assertEquals(challengeFile.getFileSize(), result.getFileSize());

        verify(challengeFileRepository).findById(anyLong());
    }

    @Test
    @DisplayName("챌린지 아이디에 따른 챌린지 파일 목록 조회 테스트")
    void testFindFileList() {
        when(challengeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challengeFile.getChallenge()));

        List<ChallengeFile> result = challengeFileService.findFileList(challengeFile.getChallenge().getId());

        assertNotNull(result);
        assertEquals(challengeFile.getOrigFileName(), result.get(0).getOrigFileName());
        assertEquals(challengeFile.getFilePath(), result.get(0).getFilePath());
        assertEquals(challengeFile.getFileSize(), result.get(0).getFileSize());

        verify(challengeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("챌린지 아이디에 따른 챌린지 파일 아이디 목록 조회 테스트")
    void testGetFileListByChallenge() {
        when(challengeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challengeFile.getChallenge()));

        List<FileIdResponseDto> result = challengeFileService.getFileListByCommunityOrChallenge(challengeFile.getChallenge().getId());

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(challengeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("챌린지 파일 삭제 테스트")
    void testDeleteFile() {
        doNothing().when(challengeFileRepository).deleteById(anyLong());

        challengeFileService.deleteFile(challengeFile.getId());

        verify(challengeFileRepository).deleteById(anyLong());
    }
}