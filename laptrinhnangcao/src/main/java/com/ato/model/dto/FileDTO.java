package com.ato.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private String fileName;
    private String fileType;
    private long size;
    private String pathLocation;

}
