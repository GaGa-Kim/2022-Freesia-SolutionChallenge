package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    private Long id;
    private User uid;
    private String title;
    private String contents;
    //private List<Long> imageId;
    private List<Long> filePathId;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    // private String createdDate;
    // private String modifiedDate;


    public ChallengeResponseDto(Challenge entity, List<Long> filePath) {
        this.id = entity.getId();
        this.uid = entity.getUser();
        this.title = entity.getTitle();
        this.contents = entity.getContent();
        //this.imageId = imageId;
        this.filePathId = filePath;
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }
}
