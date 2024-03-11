package com.freesia.imyourfreesia.service;

import com.freesia.imyourfreesia.domain.community.CommunityPhoto;
import com.freesia.imyourfreesia.domain.community.CommunityPhotoRepository;
import com.freesia.imyourfreesia.dto.community.PhotoDto;
import com.freesia.imyourfreesia.dto.community.PhotoResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final CommunityPhotoRepository communityPhotoRepository;

    // 이미지 아이디에 따른 이미지 개별 조회
    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id) {

        CommunityPhoto communityPhoto = communityPhotoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        PhotoDto photoDto = PhotoDto.builder()
                .origFileName(communityPhoto.getOrigFileName())
                .filePath(communityPhoto.getFilePath())
                .fileSize(communityPhoto.getFileSize())
                .build();

        return photoDto;
    }

    // 게시글 아이디에 따른 이미지 전체 조회
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> findAllByCommunity(Long communityId) {

        List<CommunityPhoto> communityPhotoList = communityPhotoRepository.findAllByCommunity(communityId);

        return communityPhotoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }

    // 이미지 삭제
    @Transactional
    public void delete(Long id) {
        CommunityPhoto communityPhoto = communityPhotoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id=" + id));
        communityPhotoRepository.delete(communityPhoto);
    }
}
