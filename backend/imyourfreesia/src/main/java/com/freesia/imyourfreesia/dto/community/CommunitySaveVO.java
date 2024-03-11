package com.freesia.imyourfreesia.dto.community;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommunitySaveVO {
    private String email;

    private String title;

    private String content;

    private String category;

    private List<MultipartFile> files;
}
