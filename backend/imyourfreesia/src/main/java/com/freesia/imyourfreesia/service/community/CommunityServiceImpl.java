package com.freesia.imyourfreesia.service.community;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.file.FileHandler;
import com.freesia.imyourfreesia.service.file.FileService;
import com.freesia.imyourfreesia.service.user.UserService;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final FileService communityFileServiceImpl;

    private final FileHandler fileHandler;
    private final UserService userService;

    @Override
    public CommunityResponseDto saveCommunity(CommunitySaveRequestDto requestDto, List<MultipartFile> files) throws Exception {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Community community = requestDto.toEntity();
        community.setUser(user);
        saveCommunityFiles(community, files);
        communityRepository.save(community);
        return new CommunityResponseDto(community, getFileIdListByCommunity(community));
    }

    @Override
    public List<CommunityListResponseDto> findCommunityByCategory(String category) {
        return communityRepository.findByCategory(category)
                .stream()
                .map(CommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityResponseDto findCommunityById(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(NotFoundException::new);
        return new CommunityResponseDto(community, getFileIdListByCommunity(community));
    }

    @Override
    public CommunityResponseDto updateCommunity(Long communityId, CommunityUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Community community = communityRepository.findById(communityId).orElseThrow(NotFoundException::new);
        if (!files.isEmpty()) {
            community.removeAllFiles();
            saveCommunityFiles(community, files);
        }
        community.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory());
        return new CommunityResponseDto(community, getFileIdListByCommunity(community));
    }

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);
    }

    @Override
    public List<CommunityListResponseDto> findCommunityByUser(String email) {
        User user = userService.findUserByEmail(email);
        return communityRepository.findByUser(user)
                .stream()
                .map(CommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

    private void saveCommunityFiles(Community community, List<MultipartFile> files) throws IOException {
        List<FileSaveRequestDto> savedFiles = fileHandler.saveFiles(files);
        for (FileSaveRequestDto savedFile : savedFiles) {
            CommunityFile communityFile = savedFile.toCommunityFileEntity();
            community.addFile(communityFile);
            communityFileServiceImpl.saveFile(communityFile);
        }
    }

    private List<Long> getFileIdListByCommunity(Community community) {
        return community.getFiles().stream()
                .map(CommunityFile::getId)
                .collect(Collectors.toList());
    }
}