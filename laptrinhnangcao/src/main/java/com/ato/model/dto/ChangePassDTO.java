package com.ato.model.dto;

import lombok.Data;

@Data
public class ChangePassDTO {
    String email;
    String userName;
    String oldPass;
    String newPass;
    String comPass;
    String key;
}
