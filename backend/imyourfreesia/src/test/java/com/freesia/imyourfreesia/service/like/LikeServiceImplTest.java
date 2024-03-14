package com.freesia.imyourfreesia.service.like;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.freesia.imyourfreesia.domain.community.CommunityTest;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeRepository;
import com.freesia.imyourfreesia.domain.like.LikeTest;
import com.freesia.imyourfreesia.domain.user.UserTest;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDtoTest;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LikeServiceImplTest {
    private Like like;

    @Mock
    private LikeRepository likeRepository;
    @Mock
    private UserService userService;
    @Mock
    private CommunityService communityService;
    @InjectMocks
    private LikeServiceImpl likeService;

    @BeforeEach
    void setUp() {
        like = LikeTest.testLike();
        like.setUser(UserTest.testUser());
        like.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("좋아요 저장 테스트")
    void testSaveLike() {
        when(userService.findUserByEmail(anyString())).thenReturn(like.getUser());
        when(communityService.findCommunityById(anyLong())).thenReturn(like.getCommunity());
        when(likeRepository.save(any(Like.class))).thenReturn(like);

        LikeSaveRequestDto likeSaveRequestDto = LikeSaveRequestDtoTest.testLikeSaveRequestDto(like);
        LikeResponseDto result = likeService.saveLike(likeSaveRequestDto);

        assertNotNull(result);
        assertEquals(like.getUser().getId(), result.getUserId());
        assertEquals(like.getCommunity().getId(), result.getCommunityId());

        verify(userService).findUserByEmail(anyString());
        verify(communityService).findCommunityById(anyLong());
        verify(likeRepository).save(any(Like.class));
    }

    @Test
    @DisplayName("좋아요 삭제 테스트")
    void testDeleteLike() {
        doNothing().when(likeRepository).deleteById(anyLong());

        likeService.deleteLike(like.getId());

        verify(likeRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티에 따른 좋아요 목록 조회 테스트")
    void testGetListListByCommunity() {
        when(communityService.findCommunityById(anyLong())).thenReturn(like.getCommunity());

        List<LikeListResponseDto> result = likeService.getListListByCommunity(like.getCommunity().getId());

        assertNotNull(result);
        assertEquals(like.getId(), result.get(0).getLikeId());
        assertEquals(like.getUser().getId(), result.get(0).getUserId());
        assertEquals(like.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(like.getCommunity().getTitle(), result.get(0).getTitle());
        assertEquals(like.getCommunity().getContent(), result.get(0).getContent());
        assertEquals(like.getCommunity().getCategory().getCategoryName(), result.get(0).getCategory());

        verify(communityService).findCommunityById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티에 따른 좋아요 개수 테스트")
    void testCountByCommunity() {
        when(communityService.findCommunityById(anyLong())).thenReturn(like.getCommunity());

        int result = likeService.countByCommunity(like.getCommunity().getId());

        assertEquals(1, result);

        verify(communityService).findCommunityById(anyLong());
    }

    @Test
    @DisplayName("회원이 작성한 좋아요 목록 조회 테스트")
    void testGetLikeListByUser() {
        when(userService.findUserByEmail(anyString())).thenReturn(like.getUser());
        when(likeRepository.findByUser(any())).thenReturn(Collections.singletonList(like));

        List<LikeListResponseDto> result = likeService.getLikeListByUser(like.getUser().getEmail());

        assertNotNull(result);
        assertEquals(like.getId(), result.get(0).getLikeId());
        assertEquals(like.getUser().getId(), result.get(0).getUserId());
        assertEquals(like.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(like.getCommunity().getTitle(), result.get(0).getTitle());
        assertEquals(like.getCommunity().getContent(), result.get(0).getContent());
        assertEquals(like.getCommunity().getCategory().getCategoryName(), result.get(0).getCategory());

        verify(userService).findUserByEmail(anyString());
        verify(likeRepository).findByUser(any());
    }
}