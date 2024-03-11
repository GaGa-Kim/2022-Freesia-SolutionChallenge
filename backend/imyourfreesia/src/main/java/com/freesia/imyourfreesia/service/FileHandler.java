package com.freesia.imyourfreesia.service;

import com.freesia.imyourfreesia.domain.community.CommunityPhoto;
import com.freesia.imyourfreesia.dto.community.PhotoDto;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHandler {

    private final PhotoService photoService;

    public FileHandler(PhotoService photoService) {
        this.photoService = photoService;
    }

    public List<CommunityPhoto> parseFileInfo(
            List<MultipartFile> multipartFiles
    ) throws Exception {

        List<CommunityPhoto> fileList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "images" + File.separator + current_date;
            File file = new File(path);

            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                if (!wasSuccessful) {
                    System.out.println("file: was not successful");
                }
            }

            for (MultipartFile multipartFile : multipartFiles) {

                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else {
                        break;
                    }
                }

                String new_file_name = System.nanoTime() + originalFileExtension;

                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();

                CommunityPhoto communityPhoto = CommunityPhoto.builder()
                        .origFileName(photoDto.getOrigFileName())
                        .filePath(photoDto.getFilePath())
                        .fileSize(photoDto.getFileSize())
                        .build();

                fileList.add(communityPhoto);

                file = new File(absolutePath + path + File.separator + new_file_name);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }

        return fileList;
    }
}