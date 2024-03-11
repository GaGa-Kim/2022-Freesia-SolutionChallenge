package com.freesia.imyourfreesia.service;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityPhoto;
import com.freesia.imyourfreesia.domain.community.CommunityPhotoRepository;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.domain.user.UserRepository;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityPhotoRepository communityPhotoRepository;
    private final FileHandler fileHandler;

    // 게시글 저장
    @Transactional
    public Long save(CommunitySaveRequestDto communitySaveRequestDto, List<MultipartFile> files) throws Exception {
        User user = userRepository.findByEmail(communitySaveRequestDto.getEmail());
        Community community = communitySaveRequestDto.toEntity();
        community.setUser(user);

        List<CommunityPhoto> communityPhotoList = fileHandler.parseFileInfo(files);

        if (!communityPhotoList.isEmpty()) {
            for (CommunityPhoto communityPhoto : communityPhotoList) {
                community.addPhoto(communityPhotoRepository.save(communityPhoto));
            }
        }

        return communityRepository.save(community).getId();
    }

    // 게시판 리스트 조회
    @Transactional
    public List<Community> list(String category) {
        return communityRepository.findByCategory(category);
    }

    // 게시글 상세페이지 조회
    @Transactional
    public CommunityResponseDto findById(Long id, List<Long> fileId) {
        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new CommunityResponseDto(community, fileId);
    }

    // 게시글 수정 - 사진 없을 때
    @Transactional
    public Long update(Long id, CommunityUpdateRequestDto communityUpdateRequestDto) throws Exception {
        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        community.update(communityUpdateRequestDto.getTitle(), communityUpdateRequestDto.getContent(), community.getCategory());

        return id;
    }

    // 게시글 수정 - 사진 있을 때
    @Transactional
    public Long updateWithImage(Long id, CommunityUpdateRequestDto communityUpdateRequestDto, List<MultipartFile> files) throws Exception {
        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        List<CommunityPhoto> communityPhotoList = fileHandler.parseFileInfo(files);

        if (!communityPhotoList.isEmpty()) {
            for (CommunityPhoto communityPhoto : communityPhotoList) {
                communityPhoto.setCommunity(community);
                communityPhotoRepository.save(communityPhoto);
            }
        }

        community.update(communityUpdateRequestDto.getTitle(), communityUpdateRequestDto.getContent(), community.getCategory());

        return id;
    }


    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        communityRepository.delete(community);
    }

    // 이메일로 카테고리에서 내 게시글 조회
    @Transactional
    public List<Community> findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return communityRepository.findByUser(user);
    }

    /* 마이페이지 커뮤니티 조회 */
    @Transactional(readOnly = true)
    public List<CommunityListResponseDto> findByUid(String email) {
        User user = userRepository.findByEmail(email);

        return communityRepository.findByUser(user)
                .stream()
                .map(CommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

}