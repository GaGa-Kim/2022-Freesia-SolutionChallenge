package com.freesia.imyourfreesia.service.like;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.likes.LikesListResponseDto;
import com.freesia.imyourfreesia.dto.likes.LikesSaveRequestDto;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final CommunityRepository communityRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    /* 좋아요 설정 */
    @Transactional
    public Long likes(LikesSaveRequestDto requestDto) {
        User user = userService.findUserByEmail(requestDto.getUid());
        Community community = communityRepository.findById(requestDto.getPid())
                .orElseThrow(IllegalArgumentException::new);

        Like likes = new Like();
        likes.setUser(user);
        likes.setCommunity(community);

        List<Like> likeList = new ArrayList<>();
        likeList.add(likes);

        if (!likeList.isEmpty()) {
            for (Like like : likeList) {
                community.addLike(likeRepository.save(like));
            }
        }

        return likeRepository.save(likes).getId();
    }

    /* 좋아요 해제 */
    @Transactional
    public void unLikes(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        likeRepository.delete(like);
    }

    /* 좋아요 목록 조회 */
    @Transactional(readOnly = true)
    public List<LikesListResponseDto> findAllByPid(Long pid) {
        Community community = communityRepository.findById(pid)
                .orElseThrow(IllegalArgumentException::new);

        return likeRepository.findAllByCommunity(community)
                .stream()
                .map(LikesListResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 좋아요 개수 조회 */
    @Transactional
    public Long countByPid(Long pid) {
        Community community = communityRepository.findById(pid)
                .orElseThrow(IllegalArgumentException::new);
        return likeRepository.countByCommunity(community);
    }

    /* 마이페이지 북마크 조회 */
    @Transactional(readOnly = true)
    public List<LikesListResponseDto> findByUid(String email) {
        User user = userService.findUserByEmail(email);

        return likeRepository.findByUser(user)
                .stream()
                .map(LikesListResponseDto::new)
                .collect(Collectors.toList());
    }
}
