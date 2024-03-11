package com.freesia.imyourfreesia.dto.challenge;

import java.util.List;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChallengeSaveVO {
    private Long uid;
    private String title;
    private String contents;
    private List<MultipartFile> files;
}
