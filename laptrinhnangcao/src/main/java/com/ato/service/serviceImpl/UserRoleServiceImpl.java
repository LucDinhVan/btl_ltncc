package com.ato.service.serviceImpl;

import com.ato.dao.UserRoleDAO;
import com.ato.model.bo.UserRole;
import com.ato.service.IUserRoleService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Log4j2
@Service
@Transactional
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    UserRoleDAO userRoleDAO;

    @Override
    public Long insert(UserRole obj) {
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            return userRoleDAO.insert(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới quyền - user không thành công");
        }
    }

}
