package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class CommunityResponseDto {
    @ApiModelProperty(example = "커뮤니티 아이디")
    private final Long id;

    @ApiModelProperty(example = "커뮤니티 작성자 아이디")
    private final Long uid;

    @ApiModelProperty(example = "커뮤니티 작성자 이메일")
    private final String email;

    @ApiModelProperty(example = "커뮤니티 작성자 닉네임")
    private final String nickName;

    @ApiModelProperty(example = "커뮤니티 제목")
    private final String title;

    @ApiModelProperty(example = "커뮤니티 내용")
    private final String content;

    @ApiModelProperty(example = "파일 아이디 목록")
    private final List<Long> fileId;

    @ApiModelProperty(example = "커뮤니티 카테고리")
    private final String category;

    @ApiModelProperty(notes = "커뮤니티 생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "커뮤니티 수정 날짜")
    private final LocalDate modifiedDate;

    public CommunityResponseDto(Community community, List<Long> fileIdList) {
        this.id = community.getId();
        this.uid = community.getUser().getId();
        this.createdDate = community.getCreatedDate();
        this.email = community.getUser().getEmail();
        this.nickName = community.getUser().getNickName();
        this.title = community.getTitle();
        this.content = community.getContent();
        this.fileId = fileIdList;
        this.category = community.getCategory();
        this.modifiedDate = community.getModifiedDate();
    }
}