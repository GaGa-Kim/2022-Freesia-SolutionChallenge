package com.freesia.imyourfreesia.service.comment;

import com.freesia.imyourfreesia.domain.comment.Comment;
import com.freesia.imyourfreesia.domain.comment.CommentRepository;
import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.comment.CommentListResponseDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommunityService communityService;

    @Override
    public List<CommentListResponseDto> saveComment(CommentSaveRequestDto requestDto) {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Community community = communityService.findCommunityById(requestDto.getPid());
        Comment comment = requestDto.toEntity();
        comment.setUser(user);
        comment.setCommunity(community);
        commentRepository.save(comment);
        community.addComment(comment);
        return findAllCommentByCommunity(community);
    }

    @Override
    public List<CommentListResponseDto> findAllCommentByCommunityId(Long communityId) {
        Community community = communityService.findCommunityById(communityId);
        return findAllCommentByCommunity(community);
    }

    @Override
    public List<CommentListResponseDto> updateComment(Long commentId, CommentUpdateRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
        comment.update(requestDto.getContent());
        Community community = communityService.findCommunityById(requestDto.getCommunityId());
        return findAllCommentByCommunity(community);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private List<CommentListResponseDto> findAllCommentByCommunity(Community community) {
        return commentRepository.findAllByCommunity(community)
                .stream()
                .map(CommentListResponseDto::new)
                .collect(Collectors.toList());
    }
}
