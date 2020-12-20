package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Objects;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ObjectsDTO extends AtoBaseDTO {
    private Long id;
    private Long parenId;
    @Size(max = 60, message = "{common.object.name} {common.maxLength}")
    @NotBlank(message = "{common.object.name} {common.required}")
    private String name;
    @Size(max = 50, message = "{common.object.code} {common.maxLength}")
    @NotBlank(message = "{common.object.code] {common.required}")
    private String code;
    @Size(max = 100, message = "{common.description} {common.maxLength}")
    private String description;
    private Long status;
    @Size(max = 30, message = "{common.object.icon} {common.maxLength}")
    private String icon;
    @Size(max = 60, message = "{common.object.path} {common.maxLength}")
    private String path;
    private Long type;
    private Timestamp updateTime;
    private String codeAction;
    private List<RoleActionDTO> role;
    private Long checked;

    public Objects toModel() {
        Objects Objects = new Objects();
        Objects.setId(this.id);
        Objects.setParenId(this.parenId);
        Objects.setName(this.name);
        Objects.setCode(this.code);
        Objects.setDescription(this.description);
        Objects.setStatus(this.status);
        Objects.setIcon(this.icon);
        Objects.setPath(this.path);
        Objects.setUpdateTime(this.updateTime);
        Objects.setType(this.type);
        return Objects;
    }

    public TreeDTO toTree() {
        TreeDTO treeDTO = new TreeDTO();
        treeDTO.setId( this.id );
        treeDTO.setCode( this.code );
        treeDTO.setParenId( this.parenId );
        treeDTO.setTitle( this.name );
        treeDTO.setRole( this.role );
        treeDTO.setIcon( this.icon );
        treeDTO.setLink( this.path );
        return treeDTO;
    }


}
