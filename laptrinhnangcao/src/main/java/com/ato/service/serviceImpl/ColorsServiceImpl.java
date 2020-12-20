package com.ato.service.serviceImpl;

import com.ato.dao.ColorDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Color;
import com.ato.model.dto.ColorDTO;
import com.ato.service.IColorService;
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
public class ColorsServiceImpl implements IColorService {
    @Autowired
    ColorDAO colorDAO;

    @Override
    public DataListDTO getAll() {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Color> list = colorDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO doSearch(ColorDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<ColorDTO> list = colorDAO.getAllAction(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(ColorDTO obj){
        Color Color = colorDAO.findByFiled("code", obj.getCode());
        if (Color != null) {
            throw new ServiceException("Mã Color đã tồn tại");
        }
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            colorDAO.insert(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới Color không thành công");
        }
    }

    @Override
    public Long update(ColorDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            colorDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(Long id){
        try {
            Color color = colorDAO.find(id);
            colorDAO.delete(color);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

}

