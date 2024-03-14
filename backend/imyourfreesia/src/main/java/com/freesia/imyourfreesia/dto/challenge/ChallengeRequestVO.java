package com.freesia.imyourfreesia.dto.challenge;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChallengeRequestVO {
    @ApiModelProperty(notes = "챌린지 작성 회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "챌린지 제목", dataType = "String", example = "제목")
    private String title;

    @ApiModelProperty(notes = "챌린지 내용", dataType = "String", example = "내용")
    private String content;

    @ApiModelProperty(notes = "챌린지 파일들")
    private List<MultipartFile> files;

    @Builder
    public ChallengeRequestVO(Long userId, String title, String content, List<MultipartFile> files) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.files = files;
    }
}