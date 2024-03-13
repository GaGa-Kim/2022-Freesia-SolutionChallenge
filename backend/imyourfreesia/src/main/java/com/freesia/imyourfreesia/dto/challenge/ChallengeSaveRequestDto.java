package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChallengeSaveRequestDto {
    @ApiModelProperty(notes = "챌린지 작성 회원 아이디", dataType = "Long", example = "1")
    @NotNull
    private Long userId;

    @ApiModelProperty(notes = "챌린지 제목", dataType = "String", example = "제목")
    @NotEmpty
    private String title;

    @ApiModelProperty(notes = "챌린지 내용", dataType = "String", example = "내용")
    @NotEmpty
    private String content;

    @Builder
    public ChallengeSaveRequestDto(ChallengeRequestVO challengeRequestVO) {
        this.userId = challengeRequestVO.getUserId();
        this.title = challengeRequestVO.getTitle();
        this.content = challengeRequestVO.getContent();

    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .content(content)
                .build();
    }
}
