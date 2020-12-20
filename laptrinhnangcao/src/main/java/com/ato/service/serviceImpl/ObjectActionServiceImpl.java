package com.ato.service.serviceImpl;

import com.ato.dao.ObjectActionDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.ObjectAction;
import com.ato.model.dto.ObjectActionDTO;
import com.ato.model.dto.RoleObjectDTO;
import com.ato.service.IObjectActionService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.IntStream;

@Log4j2
@Service
@Transactional
public class ObjectActionServiceImpl implements IObjectActionService {
    @Autowired
    ObjectActionDAO objectActionDAO;

    
    public DataListDTO doSearch(ObjectAction obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<RoleObjectDTO> list = objectActionDAO.getAllObjectAction(obj);
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    public Long insert(ObjectActionDTO obj) {
        try {
            for (int i = 0; i < obj.getListAdd().size(); i++){
                ObjectActionDTO objectActionDTO = new ObjectActionDTO();
                objectActionDTO.setIdObjects(obj.getIdObjects());
                objectActionDTO.setIdAction(obj.getListAdd().get(i));
                objectActionDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                objectActionDAO.insert(objectActionDTO.toModel());
            }
            return obj.getIdObjects();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới quyền không thành công");
        }
    }

    public Long delete(ObjectActionDTO obj) {
        try {
            IntStream.range(0, obj.getListUncheck().size()).forEach(i -> {
                ObjectActionDTO objectActionDTO = new ObjectActionDTO();
                objectActionDTO.setIdObjects(obj.getIdObjects());
                objectActionDTO.setIdAction(obj.getListUncheck().get(i));
                objectActionDAO.deleleRow(objectActionDTO);
            });
//            obj.setListUncheckToString(obj.getListUncheck().toString().substring(1, obj.getListUncheck().toString().length()-1));
//            objectActionDAO.deleleRow(obj);
            return obj.getIdObjects();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới quyền không thành công");
        }
    }



}

