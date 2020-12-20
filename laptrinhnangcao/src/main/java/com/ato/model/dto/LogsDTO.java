package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LogsDTO extends AtoBaseDTO {
    private Long id;
    private String userImpact;
    private String codeAction;
    private String content;
    private Timestamp impactTime;
    private String ip;
    private String nameClient;
}
