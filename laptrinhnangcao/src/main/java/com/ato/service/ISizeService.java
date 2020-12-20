package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.SizesDTO;

public interface ISizeService {
    DataListDTO getAll();
    DataListDTO doSearch(SizesDTO obj);
    Long insert(SizesDTO obj);
    Long update(SizesDTO obj);
    Long delete(Long id);
}
