package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.ObjectsDTO;

public interface IObjectService {
    DataListDTO getAll();
    DataListDTO getParent();
    DataListDTO doSearch(ObjectsDTO obj);
    DataListDTO getAllObjRoleAction(Long id);
    Long insert(ObjectsDTO obj);
    Long update(ObjectsDTO obj);
    Long delete(ObjectsDTO obj);
}
