package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.ActionDTO;

public interface IActionService {
    DataListDTO getAll();

    DataListDTO doSearch(ActionDTO obj);

    Long insert(ActionDTO obj);

    Long update(ActionDTO obj);

    Long delete(Long id);
}
