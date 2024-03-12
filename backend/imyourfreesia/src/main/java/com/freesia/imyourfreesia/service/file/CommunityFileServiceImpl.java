package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileRepository;
import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommunityFileServiceImpl implements FileService {
    private final CommunityFileRepository communityFileRepository;
    private final CommunityRepository communityRepository;
    private final FileHandler fileHandler;

    public void saveFile(File file) {
        communityFileRepository.save((CommunityFile) file);
    }

    // 이미지 아이디에 따른 이미지 개별 조회
    @Override
    public FileResponseDto findByFileId(Long fileId) {
        CommunityFile communityFile = communityFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
        return new FileResponseDto(communityFile);
    }

    /* 이미지 리스트 조회 */
    @Override
    public List<CommunityFile> fileList(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles();
    }

    // 게시글 아이디에 따른 이미지 전체 조회
    @Override
    public List<FileIdResponseDto> findAllFileId(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    // 이미지 삭제
    @Override
    public void deleteFile(Long id) {
        CommunityFile communityFile = communityFileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id=" + id));
        communityFileRepository.delete(communityFile);
    }

    /* 커뮤니티 이미지 ByteArray 조회 */
    @Override
    @Cacheable
    public String getFileByteArray(Long id) throws IOException {
        FileResponseDto photoDto = findByFileId(id);
        String absolutePath = fileHandler.findAbsoluteFilePath();
        String path = photoDto.getFilePath();
        // InputStream imageStream = new FileInputStream(photoDto.getFilePath());
        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
}
