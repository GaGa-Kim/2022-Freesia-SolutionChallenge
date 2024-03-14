package com.freesia.imyourfreesia.dto.community;

import com.freesia.imyourfreesia.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunitySaveRequestDto {
    @ApiModelProperty(notes = "커뮤니티 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    @Email
    private String email;

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
