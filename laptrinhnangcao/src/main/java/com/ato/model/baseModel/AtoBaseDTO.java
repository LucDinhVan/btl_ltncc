package com.ato.model.baseModel;

import lombok.Data;

@Data
public abstract class AtoBaseDTO {
    private Long status;
    private String part;
    private Integer page;
    private Integer pageSize;
    private Integer totalRecord;

}
