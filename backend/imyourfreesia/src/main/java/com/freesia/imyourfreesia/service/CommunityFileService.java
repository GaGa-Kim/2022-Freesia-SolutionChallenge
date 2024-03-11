package com.freesia.imyourfreesia.service;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileRepository;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommunityFileService {
    private final CommunityFileRepository communityFileRepository;
    private CommunityRepository communityRepository;

    // 이미지 아이디에 따른 이미지 개별 조회
    @Transactional(readOnly = true)
    public FileResponseDto findByFileId(Long id) {
        CommunityFile communityFile = communityFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
        return new FileResponseDto(communityFile);
    }

    /* 이미지 리스트 조회 */
    @Transactional(readOnly = true)
    public List<CommunityFile> imageList(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles();
    }

    // 게시글 아이디에 따른 이미지 전체 조회
    @Transactional(readOnly = true)
    public List<FileIdResponseDto> findAllByCommunity(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    // 이미지 삭제
    @Transactional
    public void delete(Long id) {
        CommunityFile communityFile = communityFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id=" + id));
        communityFileRepository.delete(communityFile);
    }
}
