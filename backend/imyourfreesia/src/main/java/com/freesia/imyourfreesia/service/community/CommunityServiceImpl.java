package com.freesia.imyourfreesia.service.community;

import com.freesia.imyourfreesia.domain.community.Category;
import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.community.CommunityRepository;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.user.User;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import com.freesia.imyourfreesia.except.NotFoundException;
import com.freesia.imyourfreesia.service.file.FileService;
import com.freesia.imyourfreesia.service.user.UserService;
import com.freesia.imyourfreesia.util.FileHandler;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final FileService communityFileServiceImpl;

    private final UserService userService;
    private final FileHandler fileHandler;

    @Override
    public CommunityResponseDto saveCommunity(CommunitySaveRequestDto requestDto, List<MultipartFile> files) throws Exception {
        User user = userService.findUserByEmail(requestDto.getEmail());
        Community community = requestDto.toEntity();
        community.setUser(user);
        saveCommunityFiles(community, files);
        communityRepository.save(community);
        return new CommunityResponseDto(community, findFileIdListByCommunity(community));
    }

    @Override
    public List<CommunityListResponseDto> getCommunityListByCategory(String category) {
        return communityRepository.findByCategory(Category.findByCategoryName(category))
                .stream()
                .map(CommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Community findCommunityById(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(NotFoundException::new);
    }

    @Override
    public CommunityResponseDto getCommunityById(Long communityId) {
        Community community = findCommunityById(communityId);
        return new CommunityResponseDto(community, findFileIdListByCommunity(community));
    }

    @Override
    public CommunityResponseDto updateCommunity(Long communityId, CommunityUpdateRequestDto requestDto, List<MultipartFile> files) throws Exception {
        Community community = findCommunityById(communityId);
        if (!files.isEmpty()) {
            community.removeAllFiles();
            saveCommunityFiles(community, files);
        }
        community.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory());
        return new CommunityResponseDto(community, findFileIdListByCommunity(community));
    }

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);
    }

    @Override
    public List<CommunityListResponseDto> getCommunityListByUser(String email) {
        User user = userService.findUserByEmail(email);
        return communityRepository.findByUser(user)
                .stream()
                .map(CommunityListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getFileByteArray(Long fileId) throws IOException {
        FileResponseDto photoDto = communityFileServiceImpl.getFileById(fileId);
        InputStream imageStream = new FileInputStream(photoDto.getFilePath());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

    private void saveCommunityFiles(Community community, List<MultipartFile> files) throws IOException {
        List<FileSaveRequestDto> savedFiles = fileHandler.saveFiles(files);
        for (FileSaveRequestDto savedFile : savedFiles) {
            CommunityFile communityFile = savedFile.toCommunityFileEntity();
            communityFile.setCommunity(community);
            communityFileServiceImpl.saveFile(communityFile);
        }
    }

    private List<Long> findFileIdListByCommunity(Community community) {
        return community.getFiles().stream()
                .map(CommunityFile::getId)
                .collect(Collectors.toList());
    }
}