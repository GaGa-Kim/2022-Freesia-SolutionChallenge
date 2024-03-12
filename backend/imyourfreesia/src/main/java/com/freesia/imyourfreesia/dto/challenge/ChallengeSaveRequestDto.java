package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeSaveRequestDto {
    @ApiModelProperty(notes = "챌린지 작성 회원 아이디")
    @NotNull
    private Long uid;

    @ApiModelProperty(notes = "챌린지 제목")
    @NotBlank
    private String title;

    @ApiModelProperty(notes = "챌린지 내용")
    @NotBlank
    private String contents;

    @Builder
    public ChallengeSaveRequestDto(ChallengeRequestVO challengeRequestVO) {
        this.uid = challengeRequestVO.getUid();
        this.title = challengeRequestVO.getTitle();
        this.contents = challengeRequestVO.getContents();

    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .content(contents)
                .build();
    }
}
