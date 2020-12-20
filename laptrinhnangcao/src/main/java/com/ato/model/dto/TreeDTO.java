package com.ato.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TreeDTO {
    private Long id;
    private Long parenId;
    private String title;
    private String code;
    private String icon;
    private String link;
    private List<TreeDTO> children;
    private List<RoleActionDTO> role;
}
