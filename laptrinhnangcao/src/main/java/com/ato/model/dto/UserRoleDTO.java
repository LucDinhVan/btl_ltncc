package com.ato.model.dto;

import com.ato.model.bo.UserRole;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserRoleDTO {
    private Long id;
    private Long idUser;
    private Long idRole;
    private Timestamp updateTime;

    public UserRole toModel() {
        UserRole userRole = new UserRole();
        userRole.setId(this.id);
        userRole.setIdUser(this.idUser);
        userRole.setIdRole(this.idRole);
        userRole.setUpdateTime(this.updateTime);
        return userRole;
    }
}
