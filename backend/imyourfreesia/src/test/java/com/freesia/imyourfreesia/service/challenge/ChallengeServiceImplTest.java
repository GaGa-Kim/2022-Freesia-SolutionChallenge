package com.freesia.imyourfreesia.service.challenge;

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

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.challenge.ChallengeRepository;
import com.freesia.imyourfreesia.domain.challenge.ChallengeTest;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import com.freesia.imyourfreesia.domain.file.ChallengeFileTest;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeListResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeRequestVOTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeResponseDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeSaveRequestDtoTest;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDto;
import com.freesia.imyourfreesia.dto.challenge.ChallengeUpdateRequestDtoTest;
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
class ChallengeServiceImplTest {
    private Challenge challenge;

    @Mock
    private ChallengeRepository challengeRepository;
    @Mock
    private FileService challengeFileServiceImpl;
    @Mock
    private UserService userService;
    @Mock
    private FileHandler fileHandler;
    @InjectMocks
    private ChallengeServiceImpl challengeService;

    @BeforeEach
    void setUp() {
        challenge = ChallengeTest.testChallenge();
        challenge.setUser(UserTest.testUser());
        ChallengeFile challengeFile = ChallengeFileTest.testChallengeFile();
        challengeFile.setChallenge(challenge);
    }

    @Test
    @DisplayName("챌린지 저장 테스트")
    void testSaveChallenge() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(challenge.getUser());
        when(fileHandler.saveFiles(anyList())).thenReturn(Collections.singletonList(FileSaveRequestDtoTest.testChallengeFileSaveRequestDto(challenge.getFiles().get(0))));
        doNothing().when(challengeFileServiceImpl).saveFile(any(ChallengeFile.class));
        when(challengeRepository.save(any(Challenge.class))).thenReturn(challenge);

        ChallengeSaveRequestDto challengeSaveRequestDto = ChallengeSaveRequestDtoTest.testChallengeSaveRequestDto(
                ChallengeRequestVOTest.testChallengeRequestVO(challenge, challenge.getUser()));
        ChallengeResponseDto result = challengeService.saveChallenge(challengeSaveRequestDto, Collections.singletonList(mock(MultipartFile.class)));

        assertNotNull(result);
        assertEquals(challenge.getUser().getEmail(), result.getEmail());
        assertEquals(challenge.getUser().getNickName(), result.getNickName());
        assertEquals(challenge.getTitle(), result.getTitle());
        assertEquals(challenge.getContent(), result.getContent());
        assertEquals(challenge.getCreatedDate(), result.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.getModifiedDate());

        verify(userService).findUserById(anyLong());
        verify(challengeRepository).save(any(Challenge.class));
    }

    @Test
    @DisplayName("챌린지 전체 목록 조회 테스트")
    void testGetAllChallengeList() {
        when(challengeRepository.findAllDesc()).thenReturn(Collections.singletonList(challenge));

        List<ChallengeListResponseDto> result = challengeService.getAllChallengeList();

        assertNotNull(result);
        assertEquals(challenge.getUser().getId(), result.get(0).getUserId());
        assertEquals(challenge.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(challenge.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(challenge.getTitle(), result.get(0).getTitle());
        assertEquals(challenge.getContent(), result.get(0).getContent());
        assertEquals(challenge.getFiles().get(0).getId(), result.get(0).getThumbnailFileId());
        assertEquals(challenge.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.get(0).getModifiedDate());

        verify(challengeRepository).findAllDesc();
    }

    @Test
    @DisplayName("챌린지 아이디에 따른 챌린지 조회 테스트")
    void testFindChallengeById() {
        when(challengeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challenge));

        Challenge result = challengeService.findChallengeById(challenge.getId());

        assertNotNull(result);
        assertEquals(challenge.getUser(), result.getUser());
        assertEquals(challenge.getTitle(), result.getTitle());
        assertEquals(challenge.getContent(), result.getContent());
        assertEquals(challenge.getFiles(), result.getFiles());
        assertEquals(challenge.getCreatedDate(), result.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.getModifiedDate());

        verify(challengeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("챌린지 아이디에 따른 챌린지 상세 조회 테스트")
    void testGetChallengeById() {
        when(challengeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challenge));

        ChallengeResponseDto result = challengeService.getChallengeById(challenge.getId());

        assertNotNull(result);
        assertEquals(challenge.getUser().getId(), result.getUserId());
        assertEquals(challenge.getUser().getEmail(), result.getEmail());
        assertEquals(challenge.getUser().getNickName(), result.getNickName());
        assertEquals(challenge.getTitle(), result.getTitle());
        assertEquals(challenge.getContent(), result.getContent());
        assertEquals(challenge.getFiles().get(0).getId(), result.getFileIdList().get(0));
        assertEquals(challenge.getCreatedDate(), result.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.getModifiedDate());

        verify(challengeRepository).findById(anyLong());

    }

    @Test
    @DisplayName("챌린지 수정 테스트")
    void testUpdateChallenge() throws Exception {
        when(challengeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(challenge));
        when(fileHandler.saveFiles(anyList())).thenReturn(Collections.singletonList(FileSaveRequestDtoTest.testChallengeFileSaveRequestDto(challenge.getFiles().get(0))));
        doNothing().when(challengeFileServiceImpl).saveFile(any(ChallengeFile.class));

        ChallengeUpdateRequestDto challengeUpdateRequestDto = ChallengeUpdateRequestDtoTest.testChallengeUpdateRequestDto(
                ChallengeRequestVOTest.testChallengeRequestVO(challenge, challenge.getUser()));
        ChallengeResponseDto result = challengeService.updateChallenge(challenge.getId(), challengeUpdateRequestDto, Collections.singletonList(mock(MultipartFile.class)));

        assertNotNull(result);
        assertEquals(challenge.getUser().getId(), result.getUserId());
        assertEquals(challenge.getUser().getEmail(), result.getEmail());
        assertEquals(challenge.getUser().getNickName(), result.getNickName());
        assertEquals(challenge.getTitle(), result.getTitle());
        assertEquals(challenge.getContent(), result.getContent());
        assertEquals(challenge.getFiles().get(0).getId(), result.getFileIdList().get(0));
        assertEquals(challenge.getCreatedDate(), result.getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.getModifiedDate());

        verify(challengeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("챌린지 삭제 테스트")
    void testDeleteChallenge() {
        doNothing().when(challengeRepository).deleteById(anyLong());

        challengeService.deleteChallenge(challenge.getId());

        verify(challengeRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("회원이 작성한 챌린지 목록 조회 테스트")
    void testGetChallengeByUser() {
        when(userService.findUserByEmail(anyString())).thenReturn(challenge.getUser());
        when(challengeRepository.findByUser(any(User.class))).thenReturn(Collections.singletonList(challenge));

        List<ChallengeListResponseDto> result = challengeService.getChallengeListByUser(challenge.getUser().getEmail());

        assertNotNull(result);
        assertEquals(challenge.getUser().getId(), result.get(0).getUserId());
        assertEquals(challenge.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(challenge.getUser().getNickName(), result.get(0).getNickName());
        assertEquals(challenge.getTitle(), result.get(0).getTitle());
        assertEquals(challenge.getContent(), result.get(0).getContent());
        assertEquals(challenge.getFiles().get(0).getId(), result.get(0).getThumbnailFileId());
        assertEquals(challenge.getCreatedDate(), result.get(0).getCreatedDate());
        assertEquals(challenge.getModifiedDate(), result.get(0).getModifiedDate());

        verify(userService).findUserByEmail(anyString());
        verify(challengeRepository).findByUser(any(User.class));
    }
}