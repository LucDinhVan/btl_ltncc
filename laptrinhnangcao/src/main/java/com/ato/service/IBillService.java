package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.BillDTO;

public interface IBillService {
    DataListDTO doSearch(BillDTO obj);
    Long insert(BillDTO obj);
    Long update(BillDTO obj);
    Long delete(Long id);
}
