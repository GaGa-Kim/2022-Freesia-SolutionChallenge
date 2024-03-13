package com.freesia.imyourfreesia.dto.community;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityUpdateRequestDto {
    @ApiModelProperty(notes = "커뮤니티 제목", dataType = "String", example = "제목")
    @NotEmpty
    private String title;

    @ApiModelProperty(notes = "커뮤니티 내용", dataType = "String", example = "내용")
    @NotEmpty
    private String content;

    @ApiModelProperty(notes = "커뮤니티 카테고리", dataType = "String", example = "worries")
    @NotEmpty
    private String category;

    @Builder
    public CommunityUpdateRequestDto(CommunityRequestVO communityRequestVO) {
        this.title = communityRequestVO.getTitle();
        this.content = communityRequestVO.getContent();
        this.category = communityRequestVO.getCategory();
    }
}
