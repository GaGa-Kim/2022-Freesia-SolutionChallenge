package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import java.io.File;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ChallengeListResponseDto {
    private Long id;
    private User uid;
    private String title;
    private String contents;
    private Long filePathId;
    private LocalDate createdDate;
    private LocalDate modifiedDate;


    public ChallengeListResponseDto(Challenge entity) {
        this.id = entity.getId();
        this.uid = entity.getUser();
        this.title = entity.getTitle();
        this.contents = entity.getContent();

        String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

        if (!entity.getPhotos().isEmpty()) {
            //this.filePath = absolutePath + entity.getImage().get(0).getFilePath();
            this.filePathId = entity.getPhotos().get(0).getId();
        } else {
            this.filePathId = null;
        }

        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
