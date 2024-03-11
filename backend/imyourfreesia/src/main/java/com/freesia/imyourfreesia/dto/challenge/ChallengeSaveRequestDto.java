package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChallengeSaveRequestDto {
    private Long uid;
    private String title;
    private String contents;


    @Builder
    public ChallengeSaveRequestDto(Long uid, String title, String contents) {
        this.uid = uid;
        this.title = title;
        this.contents = contents;

    }

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .content(contents)
                .build();
    }
}
