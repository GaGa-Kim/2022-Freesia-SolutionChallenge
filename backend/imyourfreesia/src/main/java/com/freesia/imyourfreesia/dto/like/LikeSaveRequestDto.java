package com.freesia.imyourfreesia.dto.like;

import com.freesia.imyourfreesia.domain.like.Like;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeSaveRequestDto {
    @ApiModelProperty(notes = "커뮤니티 작성 회원 이메일")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(notes = "커뮤니티 아이디")
    @NotNull
    private Long communityId;

    @Builder
    public LikeSaveRequestDto(String email, Long communityId) {
        this.email = email;
        this.communityId = communityId;
    }

    public Like toEntity() {
        return Like.builder()
                .build();
    }
}
