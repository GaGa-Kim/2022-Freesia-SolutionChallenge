package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import com.freesia.imyourfreesia.domain.file.CommunityFile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class CommunityListResponseDto {
    @ApiModelProperty(notes = "커뮤니티 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    @ApiModelProperty(notes = "커뮤니티 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "커뮤니티 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "작성 회원 닉네임", dataType = "String", example = "freesia")
    private final String nickName;

    @ApiModelProperty(notes = "커뮤니티 제목", dataType = "String", example = "제목")
    private final String title;

    @ApiModelProperty(notes = "커뮤니티 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "커뮤니티 카테고리", dataType = "String", example = "worries")
    private final String category;

    @ApiModelProperty(notes = "커뮤니티 썸네일 파일 아이디", dataType = "Long", example = "1")
    private final Long thumbnailFileId;

    @ApiModelProperty(notes = "커뮤니티 생성 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "커뮤니티 수정 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate modifiedDate;

    public CommunityListResponseDto(Community community) {
        this.communityId = community.getId();
        this.userId = community.getUser().getId();
        this.email = community.getUser().getEmail();
        this.nickName = community.getUser().getNickName();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.category = community.getCategory().getCategoryName();
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
