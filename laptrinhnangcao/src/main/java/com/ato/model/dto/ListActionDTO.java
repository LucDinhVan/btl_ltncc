package com.ato.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListActionDTO {
    private Long id;
    private List<RoleActionDTO> listAction;
}
