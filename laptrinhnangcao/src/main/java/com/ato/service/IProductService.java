package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.ProductDTO;

public interface IProductService {
    DataListDTO getAll();

    DataListDTO doSearch(ProductDTO obj);

    Long insert(ProductDTO obj);

    Long update(ProductDTO obj);

    Long delete(Long id);
}
