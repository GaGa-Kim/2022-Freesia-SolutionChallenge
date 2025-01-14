package com.freesia.imyourfreesia.domain.file;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChallengeFile extends File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challengeFileId")
    @ApiModelProperty(notes = "챌린지 파일 아이디", dataType = "Long", example = "1")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    @ApiModelProperty(notes = "챌린지", dataType = "Challenge")
    private Challenge challenge;

    @Builder
    public ChallengeFile(Long id, String origFileName, String filePath, Long fileSize) {
        super(origFileName, filePath, fileSize);
        this.id = id;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
        if (!challenge.getFiles().contains(this)) {
            challenge.getFiles().add(this);
        }
    }
}