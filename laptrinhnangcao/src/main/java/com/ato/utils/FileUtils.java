package com.ato.utils;


import com.ato.model.dto.FileDTO;
import org.hibernate.service.spi.ServiceException;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class FileUtils {

    private Path fileStorageImageLocation;

    public String uploadFile(MultipartFile file, String code) {
        createUploadFileLocation(code);
        FileDTO fileDTO = storeFile(file);
        int index = fileDTO.getPathLocation().indexOf("assets");

        // Format path file lưu vào database
        String pathFile = "";
        try {
            pathFile = fileDTO.getPathLocation().substring(index);
            pathFile = VNCharacterUtils.removeAccent(pathFile);
        } catch (Exception ex) {
            throw new ServiceException(Translator.toLocale("error_create_location"), ex);
        }
        return pathFile;
    }

    // Tạo nơi chứa các file người dùng upload
    public void createUploadFileLocation(String code) throws ServiceException {
        if (code.equals(null)) {
            throw new ServiceException(Translator.toLocale("error_create_location"));
        }
        this.fileStorageImageLocation = Paths.get("/Users/thachhieu/hieuatomta.github.io/src/assets/images/products/" + code)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageImageLocation);
        } catch (Exception ex) {
            throw new ServiceException(Translator.toLocale("error_create_location"), ex);
        }
    }

    public FileDTO storeFile(MultipartFile file) throws ServiceException {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
        String formatDateTime = localDateTime.format(formatter);
        String fileType = StringUtils.cleanPath(file.getContentType());

        // Format tên file lưu vào folder
        String fileNameFormat = formatDateTime + "_" + StringUtils.cleanPath(VNCharacterUtils.removeAccent1(file.getOriginalFilename()));
        if (fileNameFormat.contains("..")) {
            throw new ServiceException(Translator.toLocale("error_name_file") + fileNameFormat + "");
        }
        if (file.getSize() > 5000000) {
            throw new ServiceException(Translator.toLocale("error_size_file") + file.getSize() + "");
        }
        try {
            Long fileSize = file.getSize();
            Path targetLocation = Paths.get("");
            if (fileType.contains("image")) {
                targetLocation = this.fileStorageImageLocation.resolve(fileNameFormat);
            }
            FileDTO fileDTO = new FileDTO(fileNameFormat , fileType , fileSize , String.valueOf(targetLocation));
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileDTO;
        } catch (IOException ex) {
            throw new ServiceException(Translator.toLocale("error_save_file_upload") + fileNameFormat + "", ex);
        }
    }

}