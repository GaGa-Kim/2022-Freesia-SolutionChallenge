package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    @ApiModelProperty(notes = "챌린지 아이디", dataType = "Long", example = "1")
    private final Long challengeId;

    @ApiModelProperty(notes = "챌린지 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "챌린지 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "챌린지 작성 회원 닉네임", dataType = "String", example = "freesia")
    private final String nickName;

    @ApiModelProperty(notes = "챌린지 제목", dataType = "String", example = "제목")
    private final String title;

    @ApiModelProperty(notes = "챌린지 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "챌린지 파일 아이디 목록", dataType = "List<Long>", example = "[1, 2, 3]")
    private final List<Long> fileIdList;

    @ApiModelProperty(notes = "챌린지 생성 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "챌린지 수정 날짜", dataType = "LocalDate", example = "20XX.XX.XX")
    private final LocalDate modifiedDate;

    public ChallengeResponseDto(Challenge challenge, List<Long> fileIdList) {
        this.challengeId = challenge.getId();
        this.userId = challenge.getUser().getId();
        this.email = challenge.getUser().getEmail();
        this.nickName = challenge.getUser().getNickName();
        this.title = challenge.getTitle();
        this.content = challenge.getContent();
        this.fileIdList = fileIdList;
        this.createdDate = challenge.getCreatedDate();
        this.modifiedDate = challenge.getModifiedDate();
    }
}