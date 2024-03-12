package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.file.ChallengeFile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeListResponseDto {
    @ApiModelProperty(notes = "챌린지 아이디")
    private final Long id;

    @ApiModelProperty(notes = "챌린지 작성 회원 아이디")
    private final Long uid;

    @ApiModelProperty(notes = "챌린지 작성 회원 이메일")
    private final String email;

    @ApiModelProperty(notes = "챌린지 작성 회원 닉네임")
    private final String nickName;

    @ApiModelProperty(notes = "챌린지 작성 회원 프로필 사진")
    private final String profileImg;

    @ApiModelProperty(notes = "챌린지 제목")
    private final String title;

    @ApiModelProperty(notes = "챌린지 내용")
    private final String contents;

    @ApiModelProperty(notes = "챌린지 썸네일 파일 아이디")
    private final Long thumbnailFileId;

    @ApiModelProperty(notes = "챌린지 생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "챌린지 수정 날짜")
    private final LocalDate modifiedDate;

    public ChallengeListResponseDto(Challenge challenge) {
        this.id = challenge.getId();
        this.uid = challenge.getUser().getId();
        this.email = challenge.getUser().getEmail();
        this.nickName = challenge.getUser().getNickName();
        this.profileImg = challenge.getUser().getProfileImg();
        this.title = challenge.getTitle();
        this.contents = challenge.getContent();
        this.thumbnailFileId = getFileThumbnail(challenge.getFiles());
        this.createdDate = challenge.getCreatedDate();
        this.modifiedDate = challenge.getModifiedDate();
    }

    private Long getFileThumbnail(List<ChallengeFile> fileList) {
        if (fileList.isEmpty()) {
            return null;
        }
        return fileList.get(0).getId();
    }
}
