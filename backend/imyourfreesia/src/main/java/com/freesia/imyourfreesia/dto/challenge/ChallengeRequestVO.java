package com.freesia.imyourfreesia.dto.challenge;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChallengeRequestVO {
    @ApiModelProperty(notes = "회원 아이디")
    @NotNull
    private Long uid;

    @ApiModelProperty(notes = "제목")
    @NotBlank
    private String title;

    @ApiModelProperty(notes = "내용")
    @NotBlank
    private String contents;

    @ApiModelProperty(notes = "파일들")
    private List<MultipartFile> files;
}