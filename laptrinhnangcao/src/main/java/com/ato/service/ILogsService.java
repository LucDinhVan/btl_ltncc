package com.ato.service;

import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.LogsDTO;

public interface ILogsService {
    DataListDTO doSearch(LogsDTO obj);
}
