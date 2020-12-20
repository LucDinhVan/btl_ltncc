package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.ColorDTO;

public interface IColorService {
    DataListDTO getAll();

    DataListDTO doSearch(ColorDTO obj);

    Long insert(ColorDTO obj);

    Long update(ColorDTO obj);

    Long delete(Long id);
}
