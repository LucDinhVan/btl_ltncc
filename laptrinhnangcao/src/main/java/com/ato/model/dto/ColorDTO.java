package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Color;
import com.ato.model.bo.Sizes;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class ColorDTO extends AtoBaseDTO {
    private Long id;
    @Size(max = 45, message = "{common.color.name} {common.maxLength}")
    @NotBlank(message = "{common.color.name} {common.required}")
    private String code;
    @Size(max = 45, message = "{common.color.code} {common.maxLength}")
    @NotBlank(message = "{common.color.code} {common.required}")
    private String name;
    private Long status;
    @Size(max = 45, message = "{common.description} {common.maxLength}")
    private String description;
    private Timestamp updateTime;

    public Color toModel() {
        Color color = new Color();
        color.setId(this.id);
        color.setName(this.name);
        color.setCode(this.code);
        color.setDescription(this.description);
        color.setStatus(this.status);
        color.setUpdateTime(this.updateTime);
        return color;
    }
}
