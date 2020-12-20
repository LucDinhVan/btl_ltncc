package com.ato.service.serviceImpl;

import com.ato.dao.ActionsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Action;
import com.ato.model.dto.ActionDTO;
import com.ato.service.IActionService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@Service
@Transactional
public class ActionsServiceImpl implements IActionService {
    @Autowired
    ActionsDAO actionsDAO;

    @Override
    public DataListDTO getAll(){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Action> list = actionsDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO doSearch(ActionDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<ActionDTO> list = actionsDAO.getAllAction(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(ActionDTO obj){
        Action action = actionsDAO.findByFiled("code", obj.getCode());
        if (action != null) {
            throw new ServiceException("Mã Action đã tồn tại");
        }
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            actionsDAO.insert(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới action không thành công");
        }
    }

    @Override
    public Long update(ActionDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            actionsDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(Long id){
        try {
            Action action = actionsDAO.find(id);
            actionsDAO.delete(action);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

}

