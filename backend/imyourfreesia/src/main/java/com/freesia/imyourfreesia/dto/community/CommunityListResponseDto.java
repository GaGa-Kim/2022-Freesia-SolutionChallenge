package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CommunityListResponseDto {

    @ApiModelProperty(example = "게시글 아이디")
    private Long id;

    @ApiModelProperty(example = "게시글 작성자 아이디")
    private Long uid;

    @ApiModelProperty(example = "게시글 작성자 이메일")
    private String email;

    @ApiModelProperty(example = "게시글 작성자 닉네임")
    private String nickName;

    @ApiModelProperty(example = "게시글 제목")
    private String title;

    @ApiModelProperty(example = "게시글 내용")
    private String content;

    @ApiModelProperty(example = "게시글 썸네일 이미지")
    private Long thumbnailImageId;

    @ApiModelProperty(example = "카테고리")
    private String category;

    // private String createdDate;
    // private String modifiedDate;

    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public CommunityListResponseDto(Community community) {
        this.id = community.getId();
        this.uid = community.getUser().getId();
        this.email = community.getUser().getEmail();
        this.nickName = community.getUser().getNickName();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.category = community.getCategory();

        if (!community.getFiles().isEmpty()) {
            this.thumbnailImageId = community.getFiles().get(0).getId();
        } else {
            this.thumbnailImageId = null;
        }

        this.createdDate = community.getCreatedDate();
        this.modifiedDate = community.getModifiedDate();
    }
}
