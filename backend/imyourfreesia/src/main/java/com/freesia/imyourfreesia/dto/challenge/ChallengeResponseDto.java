package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import com.freesia.imyourfreesia.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    @ApiModelProperty(notes = "챌린지 아이디")
    private final Long id;

    @ApiModelProperty(notes = "작성 회원")
    private final User uid;

    @ApiModelProperty(notes = "제목")
    private final String title;

    @ApiModelProperty(notes = "내용")
    private final String contents;

    @ApiModelProperty(notes = "파일 아이디 목록")
    private final List<Long> fileIdList;

    @ApiModelProperty(notes = "생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "수정 날짜")
    private final LocalDate modifiedDate;

    public ChallengeResponseDto(Challenge challenge, List<Long> fileIdList) {
        this.id = challenge.getId();
        this.uid = challenge.getUser();
        this.title = challenge.getTitle();
        this.contents = challenge.getContent();
        this.fileIdList = fileIdList;
        this.createdDate = challenge.getCreatedDate();
        this.modifiedDate = challenge.getModifiedDate();
    }
}