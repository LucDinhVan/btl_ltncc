package com.ato.service.serviceImpl;

import com.ato.dao.ObjectsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Objects;
import com.ato.model.dto.ObjectsDTO;
import com.ato.service.IObjectService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@Service
public class ObjectsServiceImpl implements IObjectService {
    @Autowired
    ObjectsDAO objectsDAO;

    @Override
    public DataListDTO getAll() {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Objects> list = objectsDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO getParent(){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            String[] cars = {"parenId"};
            Object obj1[] = {0L};
            List<Objects> list = objectsDAO.find(cars, obj1);
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO doSearch(ObjectsDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<ObjectsDTO> list = objectsDAO.getAllObjects(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO getAllObjRoleAction(Long id){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<ObjectsDTO> list = objectsDAO.getAllObjRoleAction(id);
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(ObjectsDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            objectsDAO.insert(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long update(ObjectsDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            objectsDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(ObjectsDTO obj){
        try {
            Objects objects = objectsDAO.find( obj.getId());
            if ( objects == null){
                throw new ServiceException("Chức năng đã bị xoá trước đó");
            }
            objectsDAO.delete(objects);
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }
}

