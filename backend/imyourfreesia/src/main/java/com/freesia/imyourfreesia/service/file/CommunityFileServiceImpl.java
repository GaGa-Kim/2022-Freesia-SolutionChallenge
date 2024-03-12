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
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommunityFileServiceImpl implements FileService {
    private final CommunityFileRepository communityFileRepository;
    private final CommunityRepository communityRepository;

    @Override
    public void saveFile(File file) {
        communityFileRepository.save((CommunityFile) file);
    }

    @Override
    public FileResponseDto findByFileId(Long fileId) {
        CommunityFile communityFile = communityFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
        return new FileResponseDto(communityFile);
    }

    @Override
    public List<CommunityFile> fileList(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles();
    }

    @Override
    public List<FileIdResponseDto> findAllFileId(Long communityId) {
        Community community = communityRepository.getById(communityId);
        return community.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        CommunityFile communityFile = communityFileRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id=" + fileId));
        communityFileRepository.delete(communityFile);
    }

    @Override
    public String getFileByteArray(Long fileId) throws IOException {
        FileResponseDto photoDto = findByFileId(fileId);
        InputStream imageStream = new FileInputStream(photoDto.getFilePath());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }
}
