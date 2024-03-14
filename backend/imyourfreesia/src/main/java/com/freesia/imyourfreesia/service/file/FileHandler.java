package com.freesia.imyourfreesia.service.file;

import com.freesia.imyourfreesia.dto.file.FileSaveRequestDto;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileHandler {
    private static final String IMAGE_DIRECTORY = "images";
    private static final String FILE_SEPARATOR = File.separator;

    public String saveProfile(MultipartFile multipartFiles) throws IOException {
        return Objects.requireNonNull(saveFile(multipartFiles)).getFilePath();
    }

    public List<FileSaveRequestDto> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileSaveRequestDto> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileSaveRequestDto savedFile = saveFile(multipartFile);
            fileList.add(savedFile);
        }
        return fileList;
    }

    private FileSaveRequestDto saveFile(MultipartFile multipartFile) throws IOException {
        String directoryPath = findDirectoryPath();
        File directory = createDirectory(directoryPath);
        if (directory == null) {
            return null;
        }
        String fileExtension = getFileExtension(Objects.requireNonNull(multipartFile.getContentType()));
        String newFileName = System.nanoTime() + fileExtension;
        String absoluteFilePath = getAbsoluteFilePath();

        File file = new File(absoluteFilePath + directoryPath + FILE_SEPARATOR + newFileName);
        multipartFile.transferTo(file);
        setFilePermissions(file);

        return FileSaveRequestDto.builder()
                .origFileName(multipartFile.getOriginalFilename())
                .filePath(absoluteFilePath + directoryPath + FILE_SEPARATOR + newFileName)
                .fileSize(multipartFile.getSize())
                .build();
    }

    private String findDirectoryPath() {
        LocalDateTime now = LocalDateTime.now();
        String currentDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return IMAGE_DIRECTORY + FILE_SEPARATOR + currentDate;
    }

    private File createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean directoryCreated = directory.mkdirs();
            if (!directoryCreated) {
                log.error("Failed to create directory: {}", directoryPath);
                return null;
            }
        }
        return directory;
    }

    private String getFileExtension(String contentType) {
        if (contentType == null || contentType.isEmpty()) {
            return "";
        }
        if (contentType.contains("image/jpeg")) {
            return ".jpg";
        } else if (contentType.contains("image/png")) {
            return ".png";
        } else {
            return "";
        }
    }

    private String getAbsoluteFilePath() {
        return new File("").getAbsolutePath() + FILE_SEPARATOR + FILE_SEPARATOR;
    }

    private void setFilePermissions(java.io.File file) {
        if (file != null && file.exists()) {
            file.setWritable(true);
            file.setReadable(true);
        }
    }
}
