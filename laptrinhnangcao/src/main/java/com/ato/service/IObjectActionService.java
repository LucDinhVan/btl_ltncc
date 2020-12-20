package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.ObjectAction;
import com.ato.model.dto.ObjectActionDTO;

public interface IObjectActionService {
    DataListDTO doSearch(ObjectAction obj);
    Long insert(ObjectActionDTO obj);
    Long delete(ObjectActionDTO obj);
}
