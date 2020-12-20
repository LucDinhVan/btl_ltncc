package com.ato.model.baseModel;

import lombok.Data;

import java.util.List;

@Data
public class DataListDTO {
    List<?> list;
    private Integer totalPages;

}
