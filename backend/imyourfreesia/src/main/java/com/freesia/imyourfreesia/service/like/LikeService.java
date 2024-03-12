package com.freesia.imyourfreesia.service.like;

import com.freesia.imyourfreesia.dto.like.LikeListResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeResponseDto;
import com.freesia.imyourfreesia.dto.like.LikeSaveRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface LikeService {
    /**
     * 좋아요를 저장한다.
     *
     * @param requestDto (좋아요 저장 정보를 담은 DTO)
     * @return LikeResponseDto (좋아요 정보를 담은 DTO)
     */
    @Transactional
    LikeResponseDto saveLike(LikeSaveRequestDto requestDto);

    /**
     * 좋아요를 삭제한다.
     *
     * @param id (좋아요 아이디)
     */
    @Transactional
    void deleteLike(Long id);

    /**
     * 커뮤니티 아이디에 따른 좋아요 목록을 조회한다.
     *
     * @param pid (커뮤니티 아이디)
     * @return List<LikeListResponseDto> (좋아요 정보를 담은 DTO 목록)
     */
    List<LikeListResponseDto> findAllByCommunityId(Long pid);

    /**
     * 커뮤니티 아이디에 따른 좋아요 개수를 조회한다.
     *
     * @param pid (커뮤니티 아이디)
     * @return int (좋아요 개수)
     */
    int countByCommunityId(Long pid);

    /**
     * 회원 이메일에 따른 좋아요 목록을 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<LikeListResponseDto> (좋아요 정보를 담은 DTO 목록)
     */
    List<LikeListResponseDto> findLikeByUser(String email);
}
