package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class CommunityResponseDto {
    @ApiModelProperty(example = "게시글 아이디")
    private final Long id;

    @ApiModelProperty(example = "게시글 작성자 아이디")
    private final Long userId;

    @ApiModelProperty(example = "생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(example = "게시글 작성자 이메일")
    private final String email;

    @ApiModelProperty(example = "게시글 작성자 닉네임")
    private final String nickName;

    @ApiModelProperty(example = "게시글 제목")
    private final String title;

    @ApiModelProperty(example = "게시글 내용")
    private final String content;

    @ApiModelProperty(example = "게시글 이미지")
    private final List<Long> fileId;

    @ApiModelProperty(example = "카테고리")
    private final String category;

    public CommunityResponseDto(Community community, List<Long> fileId) {
        this.id = community.getId();
        this.userId = community.getUser().getId();
        this.createdDate = community.getCreatedDate();
        this.email = community.getUser().getEmail();
        this.nickName = community.getUser().getNickName();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.fileId = fileId;
        this.category = community.getCategory();
    }
}