package com.freesia.imyourfreesia.service.like;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.like.Like;
import com.freesia.imyourfreesia.domain.like.LikeRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDto;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;

    private final UserService userService;
    private final CommunityService communityService;

    @Override
    public LikeResponseDto saveLike(LikeSaveRequestDto requestDto) {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Community community = communityService.findCommunityById(requestDto.getCommunityId());
        Like like = Like.builder().build();
        like.setUser(user);
        like.setCommunity(community);
        likeRepository.save(like);
        return new LikeResponseDto(like);
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public List<LikeListResponseDto> getListListByCommunity(Long communityId) {
        Community community = communityService.findCommunityById(communityId);
        return community.getLikes()
                .stream()
                .map(LikeListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public int countByCommunity(Long communityId) {
        return communityService.findCommunityById(communityId).getLikes().size();
    }

    @Override
    public List<LikeListResponseDto> getLikeListByUser(String email) {
        User user = userService.findUserByEmail(email);
        return likeRepository.findByUser(user)
                .stream()
                .map(LikeListResponseDto::new)
                .collect(Collectors.toList());
    }
}
