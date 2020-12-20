package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Action;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class ActionDTO extends AtoBaseDTO {

    private Long id;

    @Size(max = 60, message = "{common.action.name} {common.maxLength}")
    @NotBlank(message = "{common.action.name} {common.required}")
    private String name;

    @NotBlank(message = "{common.action.code} {common.required}")
    @Size(max = 50, message = "{common.action.code] {common.maxLength}")
    private String code;

    @Size(max = 100, message = "{common.description} {common.maxLength}")
    private String description;

    private Long status;
    private Timestamp updateTime;

    public Action toModel() {
        Action action = new Action();
        action.setId(this.id);
        action.setName(this.name);
        action.setCode(this.code);
        action.setDescription(this.description);
        action.setStatus(this.status);
        action.setUpdateTime(this.updateTime);
        return action;
    }
}
