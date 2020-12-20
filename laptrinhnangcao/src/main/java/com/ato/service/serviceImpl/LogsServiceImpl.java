package com.ato.service.serviceImpl;

import com.ato.dao.LogsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.dto.LogsDTO;
import com.ato.service.ILogsService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional
public class LogsServiceImpl implements ILogsService {
    @Autowired
    LogsDAO logsDAO;

    public DataListDTO doSearch(LogsDTO obj) {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<LogsDTO> list = logsDAO.doSearch(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }


}

