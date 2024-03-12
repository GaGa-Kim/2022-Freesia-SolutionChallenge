package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunitySaveRequestDto {
    @ApiModelProperty(example = "커뮤니티 작성 회원 이메일")
    @NotBlank
    @Email
    private String email;

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
    public CommunitySaveRequestDto(CommunityRequestVO communityRequestVO) {
        this.email = communityRequestVO.getEmail();
        this.title = communityRequestVO.getTitle();
        this.content = communityRequestVO.getContent();
        this.category = communityRequestVO.getCategory();
    }

    public Community toEntity() {
        return Community.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}
