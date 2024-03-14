package com.freesia.imyourfreesia.dto.community;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunityRequestVO {
    @ApiModelProperty(notes = "커뮤니티 작성 회원 이메일", dataType = "String", example = "freesia@gmail.com")
    private String email;

    @ApiModelProperty(notes = "커뮤니티 제목", dataType = "String", example = "제목")
    private String title;

    @ApiModelProperty(notes = "커뮤니티 내용", dataType = "String", example = "내용")
    private String content;

    @ApiModelProperty(notes = "커뮤니티 카테고리", dataType = "String", example = "worries")
    private String category;

    @ApiModelProperty(notes = "커뮤니티 파일들")
    private List<MultipartFile> files;

    @Builder
    public CommunityRequestVO(String email, String title, String content, String category, List<MultipartFile> files) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.category = category;
        this.files = files;
    }
}
