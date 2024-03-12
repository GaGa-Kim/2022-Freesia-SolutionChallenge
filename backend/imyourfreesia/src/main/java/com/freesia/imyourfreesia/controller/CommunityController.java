package com.freesia.imyourfreesia.controller;

import com.freesia.imyourfreesia.dto.community.CommunityListResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunityRequestVO;
import com.freesia.imyourfreesia.dto.community.CommunityResponseDto;
import com.freesia.imyourfreesia.dto.community.CommunitySaveRequestDto;
import com.freesia.imyourfreesia.dto.community.CommunityUpdateRequestDto;
import com.freesia.imyourfreesia.service.community.CommunityService;
import com.freesia.imyourfreesia.service.file.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Community API (커뮤니티 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommunityController {
    private final CommunityService communityService;
    private final FileService communityFileServiceImpl;

    @PostMapping(value = "/api/community", consumes = {"multipart/form-data"})
    @ApiOperation(value = "커뮤니티 글 저장", notes = "커뮤니티 글 저장 API")
    public ResponseEntity<CommunityResponseDto> save(@Valid CommunityRequestVO requestVO) throws Exception {
        CommunitySaveRequestDto communitySaveRequestDto = CommunitySaveRequestDto.builder().communityRequestVO(requestVO).build();
        return ResponseEntity.ok().body(communityService.saveCommunity(communitySaveRequestDto, requestVO.getFiles()));
    }

    @GetMapping("/communities")
    @ApiOperation(value = "카테고리에 따른 (고민, 후기, 모임) 게시판 리스트 조회", notes = "카테고리에 따른 (고민, 후기, 모임) 게시판 리스트 조회 API")
    @ApiImplicitParam(name = "category", value = "카테고리명")
    public ResponseEntity<List<CommunityListResponseDto>> list(@RequestParam @NotBlank String category) throws Exception {
        return ResponseEntity.ok().body(communityService.findCommunityByCategory(category));
    }

    @GetMapping("/community")
    @ApiOperation(value = "커뮤니티 글 상세 조회", notes = "커뮤니티 글 상세 조회 API")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<CommunityResponseDto> view(@RequestParam Long id) {
        return ResponseEntity.ok().body(communityService.findCommunityDetailsById(id));
    }

    @PutMapping("/api/community")
    @ApiOperation(value = "커뮤니티 글 수정", notes = "게시글 글 수정 API")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<CommunityResponseDto> update(@RequestParam(value = "id") @NotNull Long id,
                                                       @Valid CommunityRequestVO requestVO) throws Exception {
        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDto.builder().communityRequestVO(requestVO).build();
        return ResponseEntity.ok().body(communityService.updateCommunity(id, communityUpdateRequestDto, requestVO.getFiles()));
    }

    @DeleteMapping("/api/community")
    @ApiOperation(value = "커뮤니티 글 삭제", notes = "게시글 글 삭제 API")
    @ApiImplicitParam(name = "id", value = "게시글 id", example = "1")
    public ResponseEntity<?> delete(@RequestParam @NotNull Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/community/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ApiOperation(value = "커뮤니티 이미지 ByteArray 조회", notes = "커뮤니티 이미지 ByteArray 조회 API")
    @ApiImplicitParam(name = "id", value = "커뮤니티 이미지 id", example = "1")
    public ResponseEntity<String> getImage(@RequestParam @NotNull Long id) throws IOException {
        return ResponseEntity.ok().body(communityFileServiceImpl.getFileByteArray(id));
    }
}
