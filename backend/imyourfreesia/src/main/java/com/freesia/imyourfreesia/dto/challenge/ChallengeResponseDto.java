package com.freesia.imyourfreesia.dto.challenge;

import com.freesia.imyourfreesia.domain.challenge.Challenge;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class ChallengeResponseDto {
    @ApiModelProperty(notes = "챌린지 아이디")
    private final Long id;

    @ApiModelProperty(notes = "챌린지 작성자 아이디")
    private final Long uid;

    @ApiModelProperty(notes = "챌린지 작성자 이메일")
    private final String email;

    @ApiModelProperty(notes = "챌린지 작성자 닉네임")
    private final String nickName;

    @ApiModelProperty(notes = "챌린지 제목")
    private final String title;

    @ApiModelProperty(notes = "챌린지 내용")
    private final String contents;

    @ApiModelProperty(notes = "챌린지 파일 아이디 목록")
    private final List<Long> fileIdList;

    @ApiModelProperty(notes = "챌린지 생성 날짜")
    private final LocalDate createdDate;

    @ApiModelProperty(notes = "챌린지 수정 날짜")
    private final LocalDate modifiedDate;

    public ChallengeResponseDto(Challenge challenge, List<Long> fileIdList) {
        this.id = challenge.getId();
        this.uid = challenge.getUser().getId();
        this.email = challenge.getUser().getEmail();
        this.nickName = challenge.getUser().getNickName();
        this.title = challenge.getTitle();
        this.contents = challenge.getContent();
        this.fileIdList = fileIdList;
        this.createdDate = challenge.getCreatedDate();
        this.modifiedDate = challenge.getModifiedDate();
    }
}