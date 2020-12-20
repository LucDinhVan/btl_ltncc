package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolesDTO extends AtoBaseDTO {
    private Long id;
    @Size(max = 60, message = "{common.role.name} {common.maxLength}")
    private String name;
    @Size(max = 50, message = "{common.role.code} {common.maxLength}")
    private String code;
    @Size(max = 100 , message = "{common.description} {common.maxLength}")
    private String description;
    private Long status;
    private Timestamp updateTime;


    public Role toModel() {
        Role roles = new Role();
        roles.setId(this.id);
        roles.setName(this.name);
        roles.setCode(this.code);
        roles.setDescription(this.description);
        roles.setStatus(this.status);
        roles.setUpdateTime(this.updateTime);
        return roles;
    }

}
