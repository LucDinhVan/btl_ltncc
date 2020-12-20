package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.ProductSizeColorDTO;

public interface IProductSizeColorService {
    DataListDTO getAllSizeColor(Long id);
    Long insert(ProductSizeColorDTO obj);
    Long delete(Long id);
}
