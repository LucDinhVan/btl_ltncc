package com.ato.dao;

import com.ato.dao.common.HibernateDAO;
import com.ato.model.bo.UserRole;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDAO extends HibernateDAO<UserRole, Long> {
    @Autowired
    private SessionFactory sessionFactory;
}
