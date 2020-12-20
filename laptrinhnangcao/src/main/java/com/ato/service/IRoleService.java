package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.RolesDTO;

public interface IRoleService {
    DataListDTO getAll();
    DataListDTO searchRoles(RolesDTO obj);
    Long insert(RolesDTO obj);
    Long update(RolesDTO obj);
    Long delete(Long id);
}
