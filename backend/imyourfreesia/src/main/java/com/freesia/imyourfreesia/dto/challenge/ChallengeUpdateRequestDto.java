package com.freesia.imyourfreesia.dto.challenge;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeUpdateRequestDto {
    @ApiModelProperty(notes = "챌린지 제목")
    @NotBlank
    private String title;

    @ApiModelProperty(notes = "챌린지 내용")
    @NotBlank
    private String contents;

    @Builder
    public ChallengeUpdateRequestDto(ChallengeRequestVO challengeRequestVO) {
        this.title = challengeRequestVO.getTitle();
        this.contents = challengeRequestVO.getContents();
    }
}