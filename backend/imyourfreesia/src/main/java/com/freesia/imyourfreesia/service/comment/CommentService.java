package com.freesia.imyourfreesia.service.comment;

import com.freesia.imyourfreesia.dto.comment.CommentListResponseDto;
import com.freesia.imyourfreesia.dto.comment.CommentSaveRequestDto;
import com.freesia.imyourfreesia.dto.comment.CommentUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentService {
    /**
     * 댓글을 저장한다.
     *
     * @param requestDto (댓글 저장 정보를 담은 DTO)
     * @return List<CommentListResponseDto> (댓글 정보를 담은 DTO 목록)
     */
    List<CommentListResponseDto> saveComment(CommentSaveRequestDto requestDto);

    /**
     * 커뮤니티 아이디에 따른 댓글 목록을 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return List<CommentListResponseDto> (댓글 정보를 담은 DTO 목록)
     */
    @Transactional(readOnly = true)
    List<CommentListResponseDto> findAllCommentByCommunityId(Long communityId);

    /**
     * 댓글을 수정한다.
     *
     * @param commentId  (댓글 아이디)
     * @param requestDto (댓글 수정 정보를 담은 DTO)
     * @return List<CommentListResponseDto> (댓글 정보를 담은 DTO 목록)
     */
    List<CommentListResponseDto> updateComment(Long commentId, CommentUpdateRequestDto requestDto);

    /**
     * 댓글을 삭제한다.
     *
     * @param commentId (댓글 아이디)
     */
    void deleteComment(Long commentId);
}
