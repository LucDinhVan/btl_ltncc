package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Role;
import com.ato.model.bo.Sizes;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class SizesDTO extends AtoBaseDTO {
    private Long id;
    @Size(max = 45, message = "{common.size.code} {common.maxLength}")
    @NotBlank(message = "{common.size.code} {common.required}")
    private String code;
    @Size(max = 45, message = "{common.size.name} {common.maxLength}")
    @NotBlank(message = "{common.size.name} {common.required}")
    private String name;
    private Long status;
    @Size(max = 45 , message = "{common.description} {common.maxLength}")
    private String description;
    private Timestamp updateTime;

    public Sizes toModel() {
        Sizes sizes = new Sizes();
        sizes.setId(this.id);
        sizes.setName(this.name);
        sizes.setCode(this.code);
        sizes.setDescription(this.description);
        sizes.setStatus(this.status);
        sizes.setUpdateTime(this.updateTime);
        return sizes;
    }
}
