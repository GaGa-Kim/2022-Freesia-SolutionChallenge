package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileRepository;
import com.freesia.imyourfreesia.domain.file.File;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    public FileResponseDto getFileById(Long fileId) {
        CommunityFile communityFile = communityFileRepository.findById(fileId).orElseThrow(NotFoundException::new);
        return new FileResponseDto(communityFile);
    }

    @Override
    public List<CommunityFile> findFileList(Long communityId) {
        Community community = findCommunityById(communityId);
        return community.getFiles();
    }

    @Override
    public List<FileIdResponseDto> getFileListByCommunityOrChallenge(Long communityId) {
        Community community = findCommunityById(communityId);
        return community.getFiles().stream().map(FileIdResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public void deleteFile(Long fileId) {
        communityFileRepository.deleteById(fileId);
    }

    private Community findCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(NotFoundException::new);
    }
}
