package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.RoleObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RoleObjectDTO extends AtoBaseDTO {
    private Long id;
    private Long idRole;
    private Long idObject;
    private Long idAction;
    private List<RoleObjectDTO> list;
    private Timestamp updateTime;

    public RoleObject toModel() {
        RoleObject roleObject = new RoleObject();
        roleObject.setId(this.id);
        roleObject.setIdRole(this.idRole);
        roleObject.setIdObject(this.idObject);
        roleObject.setIdAction(this.idAction);
        roleObject.setUpdateTime(this.updateTime);
        return roleObject;
    }
}
