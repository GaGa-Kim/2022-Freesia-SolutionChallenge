package com.freesia.imyourfreesia.dto.challenge;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeUpdateRequestDto {
    @ApiModelProperty(notes = "챌린지 제목", dataType = "String", example = "제목")
    @NotEmpty
    private String title;

    @ApiModelProperty(notes = "챌린지 내용", dataType = "String", example = "내용")
    @NotEmpty
    private String content;

    @Builder
    public ChallengeUpdateRequestDto(ChallengeRequestVO challengeRequestVO) {
        this.title = challengeRequestVO.getTitle();
        this.content = challengeRequestVO.getContent();
    }
}