package com.ato.model.baseModel;

import lombok.Data;

@Data
public class FileDTO {
    private String fileName;
    private String fileType;
    private long size;
    private String pathLocation;
}
