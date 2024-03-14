package com.freesia.imyourfreesia.service.community;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Validated
public interface CommunityService {
    /**
     * 커뮤니티를 저장한다.
     *
     * @param requestDto (커뮤니티 저장 정보를 담은 DTO)
     * @param files      (커뮤니티 파일들)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    CommunityResponseDto saveCommunity(@Valid CommunitySaveRequestDto requestDto, List<MultipartFile> files) throws Exception;

    /**
     * 커뮤니티 카테고리 목록을 조회한다.
     *
     * @param category (커뮤니티 카테고리)
     * @return List<CommunityListResponseDto> (커뮤니티 정보를 담은 DTO 목록)
     */
    @Transactional(readOnly = true)
    List<CommunityListResponseDto> getCommunityListByCategory(String category);

    /**
     * 커뮤니티 아이디에 따른 커뮤니티를 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return Community (커뮤니티)
     */
    @Transactional(readOnly = true)
    Community findCommunityById(Long communityId);

    /**
     * 커뮤니티 아이디에 따른 커뮤니티를 상세 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    CommunityResponseDto getCommunityById(Long communityId);

    /**
     * 커뮤니티를 수정한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @param requestDto  (커뮤니티 수정 정보를 담은 DTO)
     * @param files       (커뮤니티 파일들)
     * @return CommunityResponseDto (커뮤니티 정보를 담은 DTO)
     */
    CommunityResponseDto updateCommunity(Long communityId, @Valid CommunityUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception;

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
    List<CommunityListResponseDto> getCommunityListByUser(String email);

    /**
     * 커뮤니티 파일을 ByteArray 형식으로 조회한다.
     *
     * @param fileId (파일 아이디)
     * @return String (파일의 ByteArray 형식)
     */
    String getFileByteArray(Long fileId) throws IOException;
}