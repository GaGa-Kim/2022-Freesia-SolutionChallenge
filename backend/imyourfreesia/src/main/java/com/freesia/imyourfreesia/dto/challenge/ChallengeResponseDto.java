package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    private final Long id;
    private final User uid;
    private final String title;
    private final String contents;
    private final List<Long> filePathId;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    public ChallengeResponseDto(Challenge entity, List<Long> filePath) {
        this.id = entity.getId();
        this.uid = entity.getUser();
        this.title = entity.getTitle();
        this.contents = entity.getContent();
        this.filePathId = filePath;
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
