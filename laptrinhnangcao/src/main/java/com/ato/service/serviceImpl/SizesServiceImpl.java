package com.ato.service.serviceImpl;

import com.ato.dao.SizesDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Sizes;
import com.ato.model.dto.SizesDTO;
import com.ato.service.ISizeService;
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
public class SizesServiceImpl implements ISizeService {
    @Autowired
    SizesDAO sizesDAO;

    @Override
    public DataListDTO getAll(){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Sizes> list = sizesDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO doSearch(SizesDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<SizesDTO> list = sizesDAO.getAllAction(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(SizesDTO obj){
        Sizes Sizes = sizesDAO.findByFiled("code", obj.getCode());
        if (Sizes != null) {
            throw new ServiceException("Mã Sizes đã tồn tại");
        }
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            sizesDAO.insert(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới Sizes không thành công");
        }
    }

    @Override
    public Long update(SizesDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            sizesDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(Long id){
        try {
            Sizes sizes = sizesDAO.find(id);
            sizesDAO.delete(sizes);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

}

