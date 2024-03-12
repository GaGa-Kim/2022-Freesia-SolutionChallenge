package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class CommunityListResponseDto {
    @ApiModelProperty(example = "커뮤니티 아이디")
    private final Long id;

    @ApiModelProperty(example = "커뮤니티 작성 회원 아이디")
    private final Long uid;

    @ApiModelProperty(example = "커뮤니티 작성 회원 이메일")
    private final String email;

    @ApiModelProperty(example = "작성 회원 닉네임")
    private final String nickName;

    @ApiModelProperty(example = "커뮤니티 제목")
    private final String title;

    @ApiModelProperty(example = "커뮤니티 내용")
    private final String content;

    @ApiModelProperty(example = "커뮤니티 카테고리")
    private final String category;

    @ApiModelProperty(example = "커뮤니티 썸네일 파일 아이디")
    private final Long thumbnailFileId;

    @ApiModelProperty(notes = "커뮤니티 생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "커뮤니티 수정 날짜")
    private final LocalDate modifiedDate;

    public CommunityListResponseDto(Community community) {
        this.id = community.getId();
        this.uid = community.getUser().getId();
        this.email = community.getUser().getEmail();
        this.nickName = community.getUser().getNickName();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.category = community.getCategory();
        this.thumbnailFileId = getFileThumbnail(community.getFiles());
        this.createdDate = community.getCreatedDate();
        this.modifiedDate = community.getModifiedDate();
    }

    private Long getFileThumbnail(List<CommunityFile> fileList) {
        if (fileList.isEmpty()) {
            return null;
        }
        return fileList.get(0).getId();
    }
}
