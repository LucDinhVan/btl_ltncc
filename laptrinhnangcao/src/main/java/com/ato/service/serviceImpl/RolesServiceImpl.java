package com.ato.service.serviceImpl;

import com.ato.dao.RolesDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Role;
import com.ato.model.dto.RolesDTO;
import com.ato.service.IRoleService;
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
public class RolesServiceImpl implements IRoleService {
    @Autowired
    RolesDAO rolesDAO;

    @Override
    public DataListDTO getAll(){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Role> list = rolesDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO searchRoles(RolesDTO obj){
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<RolesDTO> list = rolesDAO.getAllRoles(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(RolesDTO obj) {
        Role role = rolesDAO.findByFiled("code", obj.getCode());
        if (role != null) {
            throw new ServiceException("Mã quyền đã tồn tại");
        }
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return rolesDAO.insert(obj.toModel());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới quyền không thành công");
        }
    }

    @Override
    public Long update(RolesDTO obj){
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            Role role = obj.toModel();
            rolesDAO.update(role);
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    @Override
    public Long delete(Long id){
        try {
            Role role = rolesDAO.find(id);
            rolesDAO.delete(role);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }
}

