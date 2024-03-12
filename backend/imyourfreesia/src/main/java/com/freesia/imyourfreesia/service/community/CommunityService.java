package com.freesia.imyourfreesia.service.community;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface CommunityService {
    /**
     * 커뮤니티를 저장한다.
     *
     * @param requestDto (커뮤니티 저장 정보를 담은 DTO)
     * @param files      (커뮤니티 파일들)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    CommunityResponseDto saveCommunity(CommunitySaveRequestDto requestDto, List<MultipartFile> files) throws Exception;

    /**
     * 커뮤니티 카테고리 목록을 조회한다.
     *
     * @param category (커뮤니티 카테고리)
     * @return List<CommunityListResponseDto ( 커뮤니티 정보를 담은 DTO 목록 )
     */
    @Transactional(readOnly = true)
    List<CommunityListResponseDto> findCommunityByCategory(String category);

    /**
     * 커뮤니티 아이디에 따른 커뮤니티를 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return Community (커뮤니티)
     */
    @Transactional(readOnly = true)
    Community findCommunityById(Long communityId);

    /**
     * 커뮤니티 아이디에 따른 커뮤니티를 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    CommunityResponseDto findCommunityDetilsById(Long communityId);

    /**
     * 커뮤니티를 수정한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @param requestDto  (커뮤니티 수정 정보를 담은 DTO)
     * @param files       (커뮤니티 파일들)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    CommunityResponseDto updateCommunity(Long communityId, CommunityUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception;

    /**
     * 커뮤니티를 삭제한다.
     *
     * @param communityId (커뮤니티 아이디)
     */
    void deleteCommunity(Long communityId);

    /**
     * 회원이 작성한 커뮤니티 목록을 조회한다.
     *
     * @param email (회원 이메일)
     * @return List<CommunityListResponseDto> (커뮤니티 정보를 담은 DTO 목록)
     */
    @Transactional(readOnly = true)
    List<CommunityListResponseDto> findCommunityByUser(String email);
}