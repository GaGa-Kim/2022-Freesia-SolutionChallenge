package com.freesia.imyourfreesia.dto.community;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityUpdateRequestDto {
    @ApiModelProperty(example = "커뮤니티 제목")
    @NotBlank
    private String title;

    @ApiModelProperty(example = "커뮤니티 내용")
    @NotBlank
    private String content;

    @ApiModelProperty(example = "커뮤니티 카테고리")
    @NotBlank
    private String category;

    @Builder
    public CommunityUpdateRequestDto(CommunityRequestVO communityRequestVO) {
        this.title = communityRequestVO.getTitle();
        this.content = communityRequestVO.getContent();
        this.category = communityRequestVO.getCategory();
    }
}
