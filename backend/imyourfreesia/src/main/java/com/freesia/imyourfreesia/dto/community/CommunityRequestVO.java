package com.freesia.imyourfreesia.dto.community;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunityRequestVO {
    @ApiModelProperty(notes = "커뮤니티 작성 회원 이메일")
    @NotBlank
    @Email
    private String email;

    @ApiModelProperty(notes = "커뮤니티 제목")
    @NotBlank
    private String title;

    @ApiModelProperty(notes = "커뮤니티 내용")
    @NotBlank
    private String content;

    @ApiModelProperty(notes = "커뮤니티 카테고리")
    @NotBlank
    private String category;

    @ApiModelProperty(notes = "커뮤니티 파일들")
    private List<MultipartFile> files;
}
