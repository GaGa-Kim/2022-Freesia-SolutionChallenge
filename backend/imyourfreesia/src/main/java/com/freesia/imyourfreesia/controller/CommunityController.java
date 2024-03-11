package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import com.freesia.imyourfreesia.domain.file.CommunityFileRepository;
import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveVO;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import com.freesia.imyourfreesia.dto.file.FileIdResponseDto;
import com.freesia.imyourfreesia.dto.file.FileResponseDto;
import com.freesia.imyourfreesia.service.CommunityFileService;
import com.freesia.imyourfreesia.service.CommunityService;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"Community API"})
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityFileService communityFileService;
    private final CommunityFileRepository communityFileRepository;

    // 게시글 저장
    @PostMapping(value = "/api/community", consumes = {"multipart/form-data"})
    @ApiOperation(value = "커뮤니티 글 저장 (사용 X / 포스트맨 이용)", notes = "커뮤니티 글 저장 API")
    public Long save(
            CommunitySaveVO communitySaveVO) throws Exception {

        CommunitySaveRequestDto communitySaveRequestDto =
                CommunitySaveRequestDto.builder()
                        .email(communitySaveVO.getEmail())
                        .title(communitySaveVO.getTitle())
                        .content(communitySaveVO.getContent())
                        .category(communitySaveVO.getCategory())
                        .build();

        return communityService.save(communitySaveRequestDto, communitySaveVO.getFiles());
    }

    // 게시판 리스트 조회 (고민, 후기, 모임)
    @GetMapping("/communities")
    @ApiOperation(value = "카테고리에 따른 (고민, 후기, 모임) 게시판 리스트 조회", notes = "카테고리에 따른 (고민, 후기, 모임) 게시판 리스트 조회 API")
    @ApiImplicitParam(name = "category", value = "카테고리명")
    public List<CommunityListResponseDto> list(@RequestParam String category) throws Exception {

        List<Community> communityList = communityService.list(category);
        List<CommunityListResponseDto> communityListResponseDtoList = new ArrayList<>();

        for (Community community : communityList) {
            CommunityListResponseDto communityResponseDto = new CommunityListResponseDto(community);
            communityListResponseDtoList.add(communityResponseDto);
        }

        return communityListResponseDtoList;
    }

    // 게시글 상세 조회
    @GetMapping("/community")
    @ApiOperation(value = "커뮤니티 글 상세 조회", notes = "커뮤니티 글 상세 조회 API")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public CommunityResponseDto view(@RequestParam Long id) throws Exception {

        List<FileIdResponseDto> communityFileIdResponseDtoList = communityFileService.findAllByCommunity(id);
        List<Long> fileId = new ArrayList<>();

        for (FileIdResponseDto communityFileIdResponseDto : communityFileIdResponseDtoList) {
            fileId.add(communityFileIdResponseDto.getFileId());
        }

        return communityService.findById(id, fileId);
    }

    // 게시글 수정
    @PutMapping("/api/community")
    @ApiOperation(value = "커뮤니티 글 수정 (사용 X / 포스트맨 이용)", notes = "게시글 글 수정 API")
    public Long update(
            @RequestParam(value = "id") Long id,
            CommunitySaveVO communitySaveVO) throws Exception {

        CommunityUpdateRequestDto communityUpdateRequestDto =
                CommunityUpdateRequestDto.builder()
                        .title(communitySaveVO.getTitle())
                        .content(communitySaveVO.getContent())
                        .category(communitySaveVO.getCategory())
                        .build();

        if (communitySaveVO.getFiles() != null) {
            List<CommunityFile> dbCommunityFileList = communityFileService.imageList(id);
            List<MultipartFile> multipartList = communitySaveVO.getFiles();
            List<MultipartFile> addFileList = new ArrayList<>();

            if (CollectionUtils.isEmpty(dbCommunityFileList)) {
                if (!CollectionUtils.isEmpty(multipartList)) {
                    for (MultipartFile multipartFile : multipartList) {
                        addFileList.add(multipartFile);
                    }
                }
            } else {
                if (CollectionUtils.isEmpty(multipartList)) {
                    for (CommunityFile dbCommunityFile : dbCommunityFileList) {
                        communityFileService.delete(dbCommunityFile.getId());
                    }
                } else {
                    List<String> dbOriginNameList = new ArrayList<>();

                    for (CommunityFile dbCommunityFile : dbCommunityFileList) {
                        FileResponseDto dbCommunityPhotoSaveRequestDto = communityFileService.findByFileId(dbCommunityFile.getId());
                        String dbOrigFileName = dbCommunityPhotoSaveRequestDto.getOrigFileName();

                        if (!multipartList.contains(dbOrigFileName)) {
                            communityFileService.delete(dbCommunityFile.getId());
                        } else {
                            dbOriginNameList.add(dbOrigFileName);
                        }
                    }

                    for (MultipartFile multipartFile : multipartList) {
                        String multipartOrigName = multipartFile.getOriginalFilename();
                        if (!dbOriginNameList.contains(multipartOrigName)) {
                            addFileList.add(multipartFile);
                        }
                    }
                }
            }

            return communityService.updateWithImage(id, communityUpdateRequestDto, addFileList);
        } else {
            return communityService.update(id, communityUpdateRequestDto);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/api/community")
    @ApiOperation(value = "커뮤니티 글 삭제", notes = "게시글 글 삭제 API")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public Long delete(@RequestParam Long id) {
        communityService.delete(id);
        return id;
    }

    // 카테고리에 따른 내 게시글 가져오기
    @GetMapping("/api/community/my")
    @ApiOperation(value = "커뮤니티 내 글 조회", notes = "커뮤니티 내 글 조회 API")
    @ApiImplicitParam(name = "email", value = "사용자 이메일")
    public List<CommunityListResponseDto> my(@RequestParam String email) throws Exception {

        List<Community> communityList = communityService.findByEmail(email);
        List<CommunityListResponseDto> communityListResponseDtoList = new ArrayList<>();

        for (Community community : communityList) {
            CommunityListResponseDto communityResponseDto = new CommunityListResponseDto(community);
            communityListResponseDtoList.add(communityResponseDto);
        }

        return communityListResponseDtoList;
    }

    // 이미지 ByteArray 조회
    @GetMapping(
            value = "/community/image",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    @ApiOperation(value = "커뮤니티 이미지 ByteArray 조회", notes = "커뮤니티 이미지 ByteArray 조회 API")
    public ResponseEntity<String> getImage(@RequestParam Long id) throws IOException {
        FileResponseDto fileResponseDto = communityFileService.findByFileId(id);
        String absolutePath
                = new File("").getAbsolutePath() + File.separator + File.separator;
        String path = fileResponseDto.getFilePath();

        InputStream imageStream = new FileInputStream(absolutePath + path);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        String encodedString = Base64.getEncoder().encodeToString(imageByteArray);
        imageStream.close();

        return new ResponseEntity<>(encodedString, HttpStatus.OK);
    }

}
