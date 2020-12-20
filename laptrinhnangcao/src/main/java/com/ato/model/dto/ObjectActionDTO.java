package com.ato.model.dto;

import com.ato.model.bo.ObjectAction;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ObjectActionDTO {
    private Long id;
    private Long idObjects;
    private Long idAction;
    private Timestamp updateTime;
    private List<Long> listAdd;
    private List<Long> listUncheck;
    private String listUncheckToString;

    public ObjectAction toModel() {
        ObjectAction objectAction = new ObjectAction();
        objectAction.setId(this.id);
        objectAction.setIdObjects(this.idObjects);
        objectAction.setIdAction(this.idAction);
        objectAction.setUpdateTime(this.updateTime);
        return objectAction;
    }
}
