package com.ato.model.dto;

import com.ato.model.bo.ImageLink;
import com.ato.model.bo.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class ImageLinkDTO {
    private Long id;
    private Long idProduct;
    private String name;
    private Long status;
    private Timestamp updateTime;
    private String imageLink;
    private MultipartFile file;
}
