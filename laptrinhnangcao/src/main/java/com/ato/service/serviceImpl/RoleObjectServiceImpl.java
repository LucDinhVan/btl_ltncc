package com.ato.service.serviceImpl;

import com.ato.config.spirngConfig.LogConfig;
import com.ato.dao.LogsDAO;
import com.ato.dao.RoleObjectDAO;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.RoleObjectDTO;
import com.ato.service.IRoleObjectService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Log4j2
@Service
@Transactional
public class RoleObjectServiceImpl implements IRoleObjectService {
    @Autowired
    RoleObjectDAO roleObjectDAO;
    @Autowired
    LogConfig logConfig;
    @Autowired
    LogsDAO logsDAO;
    public Long insert(RoleObjectDTO obj){
        try {
            roleObjectDAO.deleteRoleObject(obj.getIdRole());
            for ( int i = 0; i < obj.getList().size(); i++ ){
                obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                obj.setIdAction(obj.getList().get(i).getIdAction());
                obj.setIdObject(obj.getList().get(i).getIdObject());
                roleObjectDAO.insert(obj.toModel());
            }
            return obj.getIdRole();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }
}

